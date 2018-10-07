package com.android.avanatest.products.data.interfaces;

import android.content.Context;

import com.android.avanatest.products.di.qualifiers.ApplicationContext;


public interface IDataManager {
    IAppErrorHandler getAppErrorHandler();

    IAppDataManager getAppDataManager();

    @ApplicationContext
    Context getApplicationContext();
}
