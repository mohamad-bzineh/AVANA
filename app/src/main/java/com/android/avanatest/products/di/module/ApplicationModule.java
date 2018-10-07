package com.android.avanatest.products.di.module;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.android.avanatest.products.data.prefs.AppPreferencesHelper;
import com.android.avanatest.products.data.prefs.IPreferencesHelper;
import com.android.avanatest.products.di.qualifiers.ApplicationContext;
import com.android.avanatest.products.di.scopes.ApplicationScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


@Module
public abstract class ApplicationModule {

    @Binds
    @ApplicationContext
    public abstract Context provideContext(Application application);

    @Provides
    @ApplicationScope
    public static PackageInfo packageInfo(@ApplicationContext Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Provides
    @ApplicationScope
    static IPreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }
}
