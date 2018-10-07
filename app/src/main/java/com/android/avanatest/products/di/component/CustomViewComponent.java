package com.android.avanatest.products.di.component;


import com.android.avanatest.products.di.module.CustomViewModule;
import com.android.avanatest.products.di.scopes.ViewScope;

import dagger.Component;

@ViewScope
@Component(dependencies = ApplicationComponent.class, modules = CustomViewModule.class)
public interface CustomViewComponent {

}
