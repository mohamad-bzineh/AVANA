package com.android.avanatest.products.data.interfaces;


import com.android.avanatest.products.data.model.Cart;
import com.android.avanatest.products.data.model.CartItem;
import com.android.avanatest.products.data.model.Product;
import com.android.avanatest.products.data.model.User;
import com.facebook.AccessToken;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;


public interface IAppDataManager {

    Observable<ArrayList<Product>> getProducts(int page);

    boolean isUserLoggedIn();

    String getAccessToken();

    void setAccessToken(String accessToken, AccessToken token);

    @Nullable
    User getCurrentUser();

    void setCurrentUSer(String currentUSer);

    void addToCart(CartItem cartItem);

    @Nullable
    CartItem getCartItemForProduct(Product product);

    Cart getCart();

    void clearCurrentCart();
}
