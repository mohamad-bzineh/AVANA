package com.android.avanatest.products.ui.shoppingcart;

import com.android.avanatest.products.data.model.CartItem;
import com.android.avanatest.products.ui.base.BasePresenter;
import com.android.avanatest.products.ui.base.BaseView;

import java.util.ArrayList;

public interface ShoppingCartContracts {

    interface View extends BaseView {
        void showOrderDetails(String totalPrice);

        void showCartItems(ArrayList<CartItem> cartItems);

        void openCheckoutScreen();
    }

    interface Presenter<V extends View> extends BasePresenter<V> {

        void onCheckoutClick();
    }
}
