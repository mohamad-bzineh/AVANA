package com.android.avanatest.products.data.network;

import com.android.avanatest.products.data.model.Product;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zack_barakat on 19/06/2018.
 */

public interface IApiHelper {


    @GET("/products")
    Observable<ArrayList<Product>> getProducts(@Query("page") int page, @Query("limit") int limit);

    class Factory {
        public static final int NETWORK_CALL_TIMEOUT = 30;

        public static IApiHelper create(Retrofit retrofit) {

            return retrofit.create(IApiHelper.class);
        }
    }
}
