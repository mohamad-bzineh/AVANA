package com.android.avanatest.products.data.interfaces;

import android.support.annotation.NonNull;

import com.android.avanatest.products.data.ErrorAction;

public interface IAppErrorHandler {
    @NonNull
    ErrorAction handleAppException(Throwable exception);

    String getErrorMessage(Throwable throwable);

    String getErrorCode(Throwable throwable);

}
