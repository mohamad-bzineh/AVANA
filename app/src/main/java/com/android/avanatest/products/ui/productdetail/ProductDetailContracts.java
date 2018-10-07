package com.android.avanatest.products.ui.productdetail;

import com.android.avanatest.products.data.model.Product;
import com.android.avanatest.products.ui.base.BasePresenter;
import com.android.avanatest.products.ui.base.BaseView;

public interface ProductDetailContracts {

    interface View extends BaseView {
        void showProductInfo(Product product);

        void showCartItemsCount(boolean show, String count);

        void enableAddToCartButton(boolean enable);

        void showCartItemInfo(int quantity);
    }

    interface Presenter<V extends View> extends BasePresenter<V> {

        void initProduct(Product product);

        void onAddToCartClick();

        void onProductQuantityChange(int newCount);

        void onCreateOptionMenu();
    }
}
