package com.android.avanatest.products.ui.checkout;

import com.android.avanatest.products.ui.base.BasePresenter;
import com.android.avanatest.products.ui.base.BaseView;

public interface CheckoutContracts {

    interface View extends BaseView {
        void showOrderSummary(String totalPayment, String totalItems, String name, String email, String lastCreditNumber, String cardType);

        void showMissingAddress();

        void showPaymentSuccess();

        void openProductsScreen();
    }

    interface Presenter<V extends View> extends BasePresenter<V> {

        void onPayButtonClick(String address);

        void onContinueShoppingClick();
    }
}
