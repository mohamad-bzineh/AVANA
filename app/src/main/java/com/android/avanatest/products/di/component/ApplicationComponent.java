package com.android.avanatest.products.di.component;

import android.app.Application;
import android.content.Context;

import com.android.avanatest.products.ProductsApp;
import com.android.avanatest.products.data.interfaces.IAppDataManager;
import com.android.avanatest.products.data.interfaces.IAppErrorHandler;
import com.android.avanatest.products.data.interfaces.IDataManager;
import com.android.avanatest.products.di.module.ApiModule;
import com.android.avanatest.products.di.module.ApplicationModule;
import com.android.avanatest.products.di.module.DataManagerModule;
import com.android.avanatest.products.di.qualifiers.ApplicationContext;
import com.android.avanatest.products.di.scopes.ApplicationScope;

import dagger.BindsInstance;
import dagger.Component;

@ApplicationScope
@Component(modules = {
        DataManagerModule.class,
        ApiModule.class,
        ApplicationModule.class})
public interface ApplicationComponent {

    void inject(ProductsApp app);

    @ApplicationContext
    Context context();

    Application application();

    IAppDataManager getProductsDataManager();

    IDataManager getDataManager();

    IAppErrorHandler getAppErrorHandler();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder api(ApiModule apiModule);

        ApplicationComponent build();
    }
}