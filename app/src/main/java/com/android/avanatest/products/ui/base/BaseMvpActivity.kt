package com.android.avanatest.products.ui.base

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.android.avanatest.products.ProductsApp
import com.android.avanatest.products.R
import com.android.avanatest.products.di.component.ActivityComponent
import com.android.avanatest.products.di.component.DaggerActivityComponent
import com.android.avanatest.products.di.module.ActivityModule
import com.android.avanatest.products.utils.ResourceUtil
import icepick.Icepick
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton


abstract class BaseMvpActivity : AppCompatActivity(), BaseView {

    private var mActivityComponent: ActivityComponent? = null
    private var loadingDialog: AlertDialog? = null
    private val disposableSubscription = CompositeDisposable()


    val activityComponent: ActivityComponent?
        get() {
            if (mActivityComponent == null) {
                createComponent()
            }
            return mActivityComponent
        }

    val isActive: Boolean
        get() = !this.isFinishing && !this.isDestroyed


    override fun onCreate(savedInstanceState: Bundle?) {
        assert(getPresenter() != null)
        super.onCreate(savedInstanceState)
        Icepick.restoreInstanceState(this, savedInstanceState)
        Icepick.restoreInstanceState(getPresenter(), savedInstanceState)
        activityComponent!!.inject(this)
        //set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }
        if (intent.extras != null) {
            sendExtrasToPresenter(intent.extras!!)
        }
    }

    protected abstract fun getPresenter(): BasePresenter<*>

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Icepick.saveInstanceState(this, outState)
        Icepick.saveInstanceState(getPresenter(), outState)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (getIntent().extras != null) {
            sendExtrasToPresenter(getIntent().extras!!)
        }
    }

    override fun onPause() {
        super.onPause()
        getPresenter().isViewPaused = true
    }

    override fun onResume() {
        super.onResume()
        getPresenter().isViewPaused = false
        getPresenter().onResume()
    }

    protected abstract fun sendExtrasToPresenter(extras: Bundle)

    fun createComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .applicationComponent((application as ProductsApp).component)
                .build()
    }

    override fun showProgress() {
        if (!isActive) {
            return
        }
        hideProgress()
        if (loadingDialog == null) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this, ResourceUtil.getStyleId(this, "transparentBackgroundDialog"))
            val loadingView = View.inflate(this, R.layout.loading_layout, null)
            builder.setView(loadingView)
            builder.setCancelable(false)
            loadingDialog = builder.create()
        }
        try {
            loadingDialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            loadingDialog!!.show()
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
    }

    override fun hideProgress() {
        if (!isActive) {
            return
        }
        if (loadingDialog != null) {
            try {
                loadingDialog!!.dismiss()
                loadingDialog = null
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }
        }
    }

    override fun showError(message: String, messageStyle: Int) {
        when (messageStyle) {
            ErrorView.ERROR_DIALOG -> {
                val title = getString(R.string.title_information)
                alert(title, message) {
                    okButton { }
                }.show()
            }
            ErrorView.ERROR_TOAST -> Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            else -> {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun showError(@StringRes messageResource: Int, messageStyle: Int) {
        when (messageStyle) {
            ErrorView.ERROR_DIALOG -> {
                val title = getString(R.string.title_information)
                alert(title, getString(messageResource)) {
                    okButton { }
                }.show()
            }
            ErrorView.ERROR_TOAST -> Toast.makeText(this, messageResource, Toast.LENGTH_LONG).show()
            else -> {
            }
        }
    }

    override fun destroyView() {
        finish()
    }

    override fun onDestroy() {
        disposableSubscription.clear()
        getPresenter().onDestroyView()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun addToSubscription(disposable: Disposable) {
        disposableSubscription.add(disposable)
    }
}
