package com.android.avanatest.products.ui.base;

import android.content.Context;

import com.android.avanatest.products.data.ErrorAction;
import com.android.avanatest.products.data.interfaces.IAppErrorHandler;
import com.android.avanatest.products.data.interfaces.IDataManager;
import com.android.avanatest.products.data.interfaces.IAppDataManager;
import com.android.avanatest.products.di.qualifiers.ApplicationContext;

import java.lang.ref.WeakReference;

import icepick.State;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseMvpPresenter<V extends BaseView> implements BasePresenter<V> {

    protected final IAppDataManager mAppDataManager;
    @ApplicationContext
    protected final Context mAppContext;
    protected final IAppErrorHandler mAppErrorHandler;
    protected IDataManager mDataManager;
    protected CompositeDisposable disposableSubscription = new CompositeDisposable();
    WeakReference<V> mViewWeak;

    @State
    boolean isFirstTime = true;
    private boolean isViewPaused;

    public BaseMvpPresenter(IDataManager dataManager) {
        mDataManager = dataManager;
        this.mAppDataManager = mDataManager.getAppDataManager();
        this.mAppContext = mDataManager.getApplicationContext();
        this.mAppErrorHandler = mDataManager.getAppErrorHandler();
    }

    @Override
    public void onAttachView(V view) {
        mViewWeak = new WeakReference<>(view);
        if (isFirstTime) {
            onFirstViewAttach();
            isFirstTime = false;
        } else {
            onRestoreState();
        }
    }

    protected void onRestoreState() {

    }

    /**
     * This method is called once only no matter how times the view gets recreated. useful for tracking
     */
    protected void onFirstViewAttach() {

    }

    public void addToSubscription(Disposable disposable) {
        disposableSubscription.add(disposable);
    }

    @Override
    public void onResume() {

    }

    @Override
    public boolean isViewPaused() {
        return isViewPaused;
    }

    @Override
    public void setViewPaused(boolean viewPaused) {
        this.isViewPaused = viewPaused;
    }

    public void handleApiError(Throwable throwable, int messageStyle) {
        ErrorAction action = mAppErrorHandler.handleAppException(throwable);
        handleErrorAction(messageStyle, action);
    }

    protected void handleErrorAction(int messageStyle, ErrorAction action) {
        switch (action.getActionType()) {
            case ErrorAction.ACTION_TYPE_SHOW_ERROR:
                getView().showError(action.getMessageContent(), messageStyle);
                break;
            case ErrorAction.ACTION_TYPE_DESTROY_VIEW:
                getView().destroyView();
                break;
        }
    }

    @NonNull
    public V getView() {
        return mViewWeak.get();
    }

    @Override
    public void onDestroyView() {
        disposableSubscription.clear();
    }
}
