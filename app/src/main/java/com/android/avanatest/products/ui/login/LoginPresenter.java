package com.android.avanatest.products.ui.login;

import android.os.Bundle;
import android.util.Log;

import com.android.avanatest.products.data.interfaces.IDataManager;
import com.android.avanatest.products.data.model.User;
import com.android.avanatest.products.ui.base.BaseMvpPresenter;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

public class LoginPresenter extends BaseMvpPresenter<LoginContracts.View> implements LoginContracts.Presenter<LoginContracts.View> {

    @Inject
    public LoginPresenter(IDataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void onAttachView(LoginContracts.View view) {
        super.onAttachView(view);
    }

    @Override
    public FacebookCallback<LoginResult> getFacebookCallback() {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String accessToken = loginResult.getAccessToken().getToken();
                mAppDataManager.setAccessToken(accessToken, loginResult.getAccessToken());
                getView().showMainScreen();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
            }
        };
    }
}
