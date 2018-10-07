package com.android.avanatest.products.di.module;

import android.content.Context;
import android.view.View;

import com.android.avanatest.products.di.qualifiers.ViewContext;

import dagger.Module;
import dagger.Provides;

@Module
public class CustomViewModule {

    private View view;

    public CustomViewModule(View view) {
        this.view = view;
    }

    @Provides
    @ViewContext
    Context provideContext() {
        return view.getContext();
    }

}
