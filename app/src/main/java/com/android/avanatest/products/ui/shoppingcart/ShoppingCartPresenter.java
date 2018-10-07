package com.android.avanatest.products.ui.shoppingcart;

import com.android.avanatest.products.data.interfaces.IDataManager;
import com.android.avanatest.products.data.model.Cart;
import com.android.avanatest.products.ui.base.BaseMvpPresenter;

import javax.inject.Inject;

public class ShoppingCartPresenter extends BaseMvpPresenter<ShoppingCartContracts.View> implements ShoppingCartContracts.Presenter<ShoppingCartContracts.View> {

    @Inject
    public ShoppingCartPresenter(IDataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void onAttachView(ShoppingCartContracts.View view) {
        super.onAttachView(view);
        Cart cart = mAppDataManager.getCart();
        getView().showOrderDetails(cart.getPriceWithCurrency());
        getView().showCartItems(cart.getCartItems());
    }

    @Override
    public void onResume() {
        super.onResume();
        Cart cart = mAppDataManager.getCart();
        getView().showOrderDetails(cart.getPriceWithCurrency());
        getView().showCartItems(cart.getCartItems());
    }

    @Override
    public void onCheckoutClick() {
        getView().openCheckoutScreen();
    }
}
