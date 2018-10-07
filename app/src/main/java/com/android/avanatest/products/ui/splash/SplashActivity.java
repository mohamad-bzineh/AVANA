package com.android.avanatest.products.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.avanatest.products.R;
import com.android.avanatest.products.ui.base.BaseMvpActivity;
import com.android.avanatest.products.ui.base.BasePresenter;
import com.android.avanatest.products.ui.login.LoginActivity;
import com.android.avanatest.products.ui.products.ProductsActivity;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class SplashActivity extends BaseMvpActivity implements SplashContracts.View {

    @Inject
    SplashContracts.Presenter<SplashContracts.View> mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getActivityComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupLayout();
        mPresenter.onAttachView(this);
    }

    public BasePresenter<?> getPresenter() {
        return mPresenter;
    }

    @Override
    protected void sendExtrasToPresenter(@NotNull Bundle extras) {

    }

    protected void setupLayout() {

    }

    @Override
    public void showMainScreen() {
        Intent intent = new Intent(this, ProductsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void showLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
