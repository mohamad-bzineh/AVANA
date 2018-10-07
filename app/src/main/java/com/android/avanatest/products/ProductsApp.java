package com.android.avanatest.products;

import android.app.Application;

import com.android.avanatest.products.di.component.ApplicationComponent;
import com.android.avanatest.products.di.component.DaggerApplicationComponent;
import com.android.avanatest.products.di.module.ApiModule;


public class ProductsApp extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    protected void initDagger() {

        ApplicationComponent appComponent = DaggerApplicationComponent
                .builder()
                .api(new ApiModule(BuildConfig.API))
                .application(this)
                .build();

        setComponent(appComponent);
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    public void setComponent(ApplicationComponent component) {
        this.component = component;
        component.inject(this);
    }
}
