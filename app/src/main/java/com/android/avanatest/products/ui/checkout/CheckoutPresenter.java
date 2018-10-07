package com.android.avanatest.products.ui.checkout;

import com.android.avanatest.products.data.interfaces.IDataManager;
import com.android.avanatest.products.data.model.Cart;
import com.android.avanatest.products.data.model.Product;
import com.android.avanatest.products.data.model.User;
import com.android.avanatest.products.ui.base.BaseMvpPresenter;

import javax.inject.Inject;

public class CheckoutPresenter extends BaseMvpPresenter<CheckoutContracts.View> implements CheckoutContracts.Presenter<CheckoutContracts.View> {

    @Inject
    public CheckoutPresenter(IDataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void onAttachView(CheckoutContracts.View view) {
        super.onAttachView(view);
        Cart cart = mAppDataManager.getCart();
        User user = mAppDataManager.getCurrentUser();
        if (cart != null) {
            getView().showOrderSummary(cart.getPriceWithCurrency(),
                    String.valueOf(cart.getCartItems().size()),
                    user.getName(),
                    user.getEmail(),
                    "1122",
                    "Visa");
        }
    }

    @Override
    public void onPayButtonClick(String address) {
        if (address.isEmpty()){
            getView().showMissingAddress();
            return;
        }
        getView().showPaymentSuccess();
    }

    @Override
    public void onContinueShoppingClick() {
        mAppDataManager.clearCurrentCart();
        getView().openProductsScreen();
    }
}
