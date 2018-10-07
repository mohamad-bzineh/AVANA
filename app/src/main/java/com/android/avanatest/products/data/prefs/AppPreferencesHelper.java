package com.android.avanatest.products.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.avanatest.products.data.model.User;
import com.android.avanatest.products.di.qualifiers.ApplicationContext;
import com.android.avanatest.products.di.scopes.ApplicationScope;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

@ApplicationScope
public class AppPreferencesHelper implements IPreferencesHelper {

    private static final String PREF_FILE_NAME = "products_app";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER = "user";
    private final SharedPreferences mSharedPreferences;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context) {
        mSharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public String getUserToken() {
        return mSharedPreferences.getString(KEY_TOKEN, "");
    }

    @Override
    public void setUserToken(String token) {
        mSharedPreferences.edit().putString(KEY_TOKEN, token).apply();
    }

    @Override
    public User getCurrentUser() {
        try {
            JSONObject object = new JSONObject(mSharedPreferences.getString(KEY_USER, ""));
            User user = new User();
            String firstName = object.getString("first_name");
            String lastName = object.getString("last_name");
            user.setName(firstName + " " + lastName);
            user.setEmail(object.getString("email"));
            return user;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setCurrentUser(String userObject) {
        mSharedPreferences.edit().putString(KEY_USER, userObject).apply();
    }
}
