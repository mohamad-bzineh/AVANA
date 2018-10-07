package com.android.avanatest.products.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.avanatest.products.R;
import com.android.avanatest.products.data.interfaces.IAppErrorHandler;
import com.android.avanatest.products.data.model.CommonResponseModel;
import com.android.avanatest.products.data.network.AppException;
import com.android.avanatest.products.di.qualifiers.ApplicationContext;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

public class AppErrorHandler implements IAppErrorHandler {
    private final Context appContext;

    @Inject
    public AppErrorHandler(@ApplicationContext Context appContext) {
        this.appContext = appContext;
    }

    @Override
    @NonNull
    public ErrorAction handleAppException(Throwable exception) {
        if (!(exception instanceof AppException)) {
            return getUnExpectedErrorAction();
        }
        AppException appException = (AppException) exception;
        switch (appException.getKind()) {
            case NETWORK:
                return new ErrorAction(ErrorAction.ACTION_TYPE_SHOW_ERROR, appContext.getString(R.string.title_information), appContext.getString(R.string.message_connection_error));
            case HTTP:
                CommonResponseModel apiError = appException.getErrorBodyAsCommonResponseModel();
                if (apiError == null) {
                    return getUnExpectedErrorAction();
                }
                return handleCommonResponseModel(apiError);
            case DISCONNECT:
                return new ErrorAction(ErrorAction.ACTION_TYPE_SHOW_ERROR, appContext.getString(R.string.title_information), appContext.getString(R.string.message_connect_internet));
            case UNEXPECTED:
                return getUnExpectedErrorAction();
        }

        return getUnExpectedErrorAction();
    }

    @Override
    public String getErrorMessage(Throwable throwable) {
        if (!(throwable instanceof AppException)) {
            return getUnExpectedErrorAction().getMessageContent();
        }
        AppException appException = (AppException) throwable;
        switch (appException.getKind()) {
            case NETWORK:
                return appContext.getString(R.string.message_connection_error);
            case HTTP:
                CommonResponseModel apiError = appException.getErrorBodyAsCommonResponseModel();
                if (apiError == null || apiError.getMessage() == null) {
                    return getUnExpectedErrorAction().getMessageContent();
                }
                return apiError.getMessage();
            case DISCONNECT:
                return appContext.getString(R.string.message_connect_internet);
            case UNEXPECTED:
                return getUnExpectedErrorAction().getMessageContent();
        }
        return throwable.getMessage();

    }

    @Override
    public String getErrorCode(Throwable throwable) {
        if (!(throwable instanceof AppException)) {
            return null;
        }
        AppException appException = (AppException) throwable;
        if (appException.getKind().equals(AppException.Kind.HTTP)) {
            CommonResponseModel apiError = appException.getErrorBodyAsCommonResponseModel();
            if (apiError == null || apiError.getMessage() == null) {
                return null;
            }
            return apiError.getMessage();
        }
        return null;
    }

    private ErrorAction handleCommonResponseModel(CommonResponseModel apiError) {
        if (apiError.getStatus() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
            // TODO: 20/06/2018 Handle UnAuthorized Request
        }
        if (apiError.getMessage() == null) {
            return getUnExpectedErrorAction();
        }

        return new ErrorAction(ErrorAction.ACTION_TYPE_SHOW_ERROR, appContext.getString(R.string.title_information), apiError.getMessage());
    }

    @NonNull
    private ErrorAction getUnExpectedErrorAction() {
        return new ErrorAction(ErrorAction.ACTION_TYPE_SHOW_ERROR, appContext.getString(R.string.title_information), appContext.getString(R.string.message_error_unknown));
    }
}
