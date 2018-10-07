package com.android.avanatest.products.ui.login;

import com.android.avanatest.products.ui.base.BasePresenter;
import com.android.avanatest.products.ui.base.BaseView;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;

public interface LoginContracts {

    interface View extends BaseView{

        void showMainScreen();
    }

    interface Presenter<V extends View> extends BasePresenter<V>{

        FacebookCallback<LoginResult> getFacebookCallback();
    }
}
