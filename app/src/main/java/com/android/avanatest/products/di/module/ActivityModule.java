package com.android.avanatest.products.di.module;

import android.app.Activity;
import android.content.Context;

import com.android.avanatest.products.di.qualifiers.ActivityContext;
import com.android.avanatest.products.ui.checkout.CheckoutContracts;
import com.android.avanatest.products.ui.checkout.CheckoutPresenter;
import com.android.avanatest.products.ui.login.LoginContracts;
import com.android.avanatest.products.ui.login.LoginPresenter;
import com.android.avanatest.products.ui.productdetail.ProductDetailContracts;
import com.android.avanatest.products.ui.productdetail.ProductDetailPresenter;
import com.android.avanatest.products.ui.products.ProductsContracts;
import com.android.avanatest.products.ui.products.ProductsPresenter;
import com.android.avanatest.products.ui.shoppingcart.ShoppingCartContracts;
import com.android.avanatest.products.ui.shoppingcart.ShoppingCartPresenter;
import com.android.avanatest.products.ui.splash.SplashContracts;
import com.android.avanatest.products.ui.splash.SplashPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    @Provides
    SplashContracts.Presenter<SplashContracts.View> provideSplashPresenter(SplashPresenter splashPresenter) {
        return splashPresenter;
    }


    @Provides
    LoginContracts.Presenter<LoginContracts.View> provideLoginPresenter(LoginPresenter loginPresenter) {
        return loginPresenter;
    }

    @Provides
    ProductsContracts.Presenter<ProductsContracts.View> provideProductssPresenter(ProductsPresenter productsPresenter) {
        return productsPresenter;
    }

    @Provides
    ProductDetailContracts.Presenter<ProductDetailContracts.View> provideProductDetailPresenter(ProductDetailPresenter productDetailPresenter) {
        return productDetailPresenter;
    }

    @Provides
    ShoppingCartContracts.Presenter<ShoppingCartContracts.View> provideShoppingCartPresenter(ShoppingCartPresenter shoppingCartPresenter) {
        return shoppingCartPresenter;
    }

    @Provides
    CheckoutContracts.Presenter<CheckoutContracts.View> provideCheckoutPresenter(CheckoutPresenter checkoutPresenter) {
        return checkoutPresenter;
    }
}
