package com.android.avanatest.products.ui.splash;

import com.android.avanatest.products.ui.base.BasePresenter;
import com.android.avanatest.products.ui.base.BaseView;

public interface SplashContracts {

    interface View extends BaseView {

        void showMainScreen();

        void showLoginScreen();
    }

    interface Presenter<V extends View> extends BasePresenter<V> {

    }
}
