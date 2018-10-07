package com.android.avanatest.products.ui.base;

import android.support.annotation.StringRes;

public interface ErrorView {

    int ERROR_TOAST = 1;
    int ERROR_DIALOG = 2;

    void showError(String message, int messageStyle);

    void showError(@StringRes int message, int messageStyle);

}
