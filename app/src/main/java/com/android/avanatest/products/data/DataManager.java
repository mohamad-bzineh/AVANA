package com.android.avanatest.products.data;

import android.content.Context;

import com.android.avanatest.products.data.interfaces.IAppDataManager;
import com.android.avanatest.products.data.interfaces.IAppErrorHandler;
import com.android.avanatest.products.data.interfaces.IDataManager;
import com.android.avanatest.products.di.qualifiers.ApplicationContext;
import com.android.avanatest.products.di.scopes.ApplicationScope;

import javax.inject.Inject;

@ApplicationScope
public class DataManager implements IDataManager {
    private final IAppErrorHandler mAppErrorHandler;
    private final IAppDataManager mAppDataManager;
    private final Context mApplicationContext;

    @Inject
    public DataManager(@ApplicationContext Context applicationContext, IAppErrorHandler appErrorHandler, IAppDataManager appDataManager) {
        this.mAppErrorHandler = appErrorHandler;
        this.mAppDataManager = appDataManager;
        this.mApplicationContext = applicationContext;
    }

    @Override
    public IAppErrorHandler getAppErrorHandler() {
        return mAppErrorHandler;
    }

    @Override
    public IAppDataManager getAppDataManager() {
        return mAppDataManager;
    }

    @Override
    public Context getApplicationContext() {
        return mApplicationContext;
    }
}
