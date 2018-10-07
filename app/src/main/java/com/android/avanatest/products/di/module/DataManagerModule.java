package com.android.avanatest.products.di.module;


import com.android.avanatest.products.data.AppErrorHandler;
import com.android.avanatest.products.data.DataManager;
import com.android.avanatest.products.data.AppDataManager;
import com.android.avanatest.products.data.interfaces.IAppErrorHandler;
import com.android.avanatest.products.data.interfaces.IDataManager;
import com.android.avanatest.products.data.interfaces.IAppDataManager;
import com.android.avanatest.products.di.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;


@Module(includes = {ApiModule.class})
public class DataManagerModule {

    @Provides
    @ApplicationScope
    IAppDataManager provideIProductsDataManager(AppDataManager productsDataManager) {
        return productsDataManager;
    }

    @Provides
    @ApplicationScope
    public IAppErrorHandler provideIAppErrorHandler(AppErrorHandler handler) {
        return handler;
    }

    @Provides
    @ApplicationScope
    public IDataManager provideDataManager(DataManager manager) {
        return manager;
    }
}
