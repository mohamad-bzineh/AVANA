package com.android.avanatest.products.di.component;

import com.android.avanatest.products.di.module.FragmentModule;
import com.android.avanatest.products.di.scopes.FragmentScope;
import com.android.avanatest.products.ui.base.BaseMvpFragment;

import dagger.Component;


@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(BaseMvpFragment baseMvpFragment);

}