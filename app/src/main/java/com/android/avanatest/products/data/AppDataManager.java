package com.android.avanatest.products.data;

import android.os.Bundle;
import android.util.Log;

import com.android.avanatest.products.data.interfaces.IAppDataManager;
import com.android.avanatest.products.data.model.Cart;
import com.android.avanatest.products.data.model.CartItem;
import com.android.avanatest.products.data.model.Product;
import com.android.avanatest.products.data.model.User;
import com.android.avanatest.products.data.network.IApiHelper;
import com.android.avanatest.products.data.prefs.IPreferencesHelper;
import com.android.avanatest.products.di.scopes.ApplicationScope;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;


@ApplicationScope
public class AppDataManager implements IAppDataManager {

    private final IPreferencesHelper mPreferencesHelper;
    private final IApiHelper mApiHelper;

    Cart mCurrentCart;
    ArrayList<Cart> mPreviousCarts = new ArrayList<>();

    @Inject
    public AppDataManager(IPreferencesHelper preferencesHelper, IApiHelper apiHelper) {
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
        mCurrentCart = new Cart();
    }

    @Override
    public Observable<ArrayList<Product>> getProducts(int page) {
        return mApiHelper.getProducts(page, 10);
    }

    @Override
    public boolean isUserLoggedIn() {
        return !mPreferencesHelper.getUserToken().isEmpty();
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getUserToken();
    }

    @Override
    public void setAccessToken(String accessToken, AccessToken token) {
        mPreferencesHelper.setUserToken(accessToken);
        GraphRequest request = GraphRequest.newMeRequest(token, (object, response) -> {
            Log.d("response:", response.toString());
            setCurrentUSer(object.toString());
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    @Nullable
    public User getCurrentUser() {
        return mPreferencesHelper.getCurrentUser();
    }

    @Override
    public void setCurrentUSer(String currentUSer) {
        mPreferencesHelper.setCurrentUser(currentUSer);
    }

    @Override
    public void addToCart(CartItem cartItem) {
        if (getCartItemForProduct(cartItem.getProduct()) != null) {
            mCurrentCart.getCartItems().remove(getCartItemForProduct(cartItem.getProduct()));
            mCurrentCart.getCartItems().add(cartItem);
        } else {
            mCurrentCart.getCartItems().add(cartItem);
        }
    }

    @Override
    @Nullable
    public CartItem getCartItemForProduct(Product product) {
        for (CartItem item : mCurrentCart.getCartItems()) {
            if (product.getId().equals(item.getProduct().getId())) {
                return item;
            }
        }
        return null;
    }

    @Override
    public Cart getCart() {
        return mCurrentCart;
    }

    @Override
    public void clearCurrentCart() {
        mPreviousCarts.add(mCurrentCart);
        mCurrentCart = new Cart();
    }
}
