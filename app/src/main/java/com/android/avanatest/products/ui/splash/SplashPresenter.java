package com.android.avanatest.products.ui.splash;

import com.android.avanatest.products.data.interfaces.IDataManager;
import com.android.avanatest.products.ui.base.BaseMvpPresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class SplashPresenter extends BaseMvpPresenter<SplashContracts.View> implements SplashContracts.Presenter<SplashContracts.View> {

    @Inject
    public SplashPresenter(IDataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void onAttachView(SplashContracts.View view) {
        super.onAttachView(view);
        Disposable disposable = Observable.just("")
                .delay(2000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (mDataManager.getAppDataManager().isUserLoggedIn()) {
                        getView().showMainScreen();
                    } else {
                        getView().showLoginScreen();
                    }
                }, error -> {
                    getView().showMainScreen();
                });
        addToSubscription(disposable);
    }
}
