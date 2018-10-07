package com.android.avanatest.products.di.component;


import com.android.avanatest.products.di.module.ActivityModule;
import com.android.avanatest.products.di.scopes.ActivityScope;
import com.android.avanatest.products.ui.base.BaseMvpActivity;
import com.android.avanatest.products.ui.checkout.CheckoutActivity;
import com.android.avanatest.products.ui.shoppingcart.ShoppingCartActivity;
import com.android.avanatest.products.ui.login.LoginActivity;
import com.android.avanatest.products.ui.productdetail.ProductDetailActivity;
import com.android.avanatest.products.ui.products.ProductsActivity;
import com.android.avanatest.products.ui.splash.SplashActivity;

import dagger.Component;


@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseMvpActivity activity);

    void inject(SplashActivity activity);

    void inject(LoginActivity activity);

    void inject(ProductsActivity activity);

    void inject(ProductDetailActivity activity);

    void inject(ShoppingCartActivity activity);

    void inject(CheckoutActivity activity);
}