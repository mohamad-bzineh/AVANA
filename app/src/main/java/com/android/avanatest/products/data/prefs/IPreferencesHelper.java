package com.android.avanatest.products.data.prefs;


import com.android.avanatest.products.data.model.User;

public interface IPreferencesHelper {

    void setUserToken(String token);

    String getUserToken();

    void setCurrentUser(String userObject);

    User getCurrentUser();

}
