package com.android.avanatest.products.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.avanatest.products.R;
import com.android.avanatest.products.ui.base.BaseMvpActivity;
import com.android.avanatest.products.ui.base.BasePresenter;
import com.android.avanatest.products.ui.products.ProductsActivity;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import javax.inject.Inject;

public class LoginActivity extends BaseMvpActivity implements LoginContracts.View {

    @Inject
    LoginContracts.Presenter<LoginContracts.View> mPresenter;

    CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getActivityComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        setupLayout();
        mPresenter.onAttachView(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @NotNull
    @Override
    protected BasePresenter<?> getPresenter() {
        return mPresenter;
    }

    @Override
    protected void sendExtrasToPresenter(@NotNull Bundle extras) {

    }


    private void setupLayout() {
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        loginButton.registerCallback(callbackManager, mPresenter.getFacebookCallback());
    }

    @Override
    public void showMainScreen() {
        startActivity(new Intent(this, ProductsActivity.class));
    }
}
