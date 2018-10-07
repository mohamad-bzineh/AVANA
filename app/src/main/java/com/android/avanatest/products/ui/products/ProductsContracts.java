package com.android.avanatest.products.ui.products;

import com.android.avanatest.products.data.model.Product;
import com.android.avanatest.products.ui.base.BasePresenter;
import com.android.avanatest.products.ui.base.BaseView;

import java.util.ArrayList;

public interface ProductsContracts {

    interface View extends BaseView {
        void showProducts(ArrayList<Product> products);

        void showEmptyProducts();

        void openProductDetailScreen(Product product);

        void removeLoadingMore();

        void showCartScreen();

        void showCartItemsCount(boolean showCartItemsCount, String count);
    }

    interface Presenter<V extends View> extends BasePresenter<V> {
        void onRefreshProductsClick();

        void getProducts();

        void onProductClick(Product product);

        void onCartFabClick();
    }
}
