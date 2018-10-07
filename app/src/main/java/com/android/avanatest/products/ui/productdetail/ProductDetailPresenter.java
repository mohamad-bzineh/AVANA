package com.android.avanatest.products.ui.productdetail;

import com.android.avanatest.products.data.interfaces.IDataManager;
import com.android.avanatest.products.data.model.Cart;
import com.android.avanatest.products.data.model.CartItem;
import com.android.avanatest.products.data.model.Product;
import com.android.avanatest.products.ui.base.BaseMvpPresenter;

import javax.inject.Inject;

public class ProductDetailPresenter extends BaseMvpPresenter<ProductDetailContracts.View> implements ProductDetailContracts.Presenter<ProductDetailContracts.View> {


    private Product mProduct;
    private int mQuantity;

    @Inject
    public ProductDetailPresenter(IDataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void onAttachView(ProductDetailContracts.View view) {
        super.onAttachView(view);
        getView().showProductInfo(mProduct);
        CartItem cartItem = mAppDataManager.getCartItemForProduct(mProduct);
        if (cartItem != null) {
            getView().showCartItemInfo(cartItem.getQuantity());
            mQuantity = cartItem.getQuantity();
            getView().enableAddToCartButton(mQuantity > 0);
        }
    }

    @Override
    public void initProduct(Product product) {
        mProduct = product;
    }

    @Override
    public void onAddToCartClick() {
        CartItem cartItem = new CartItem(mProduct, mQuantity);
        mAppDataManager.addToCart(cartItem);
        refreshCartCount();
    }

    @Override
    public void onProductQuantityChange(int quantity) {
        this.mQuantity = quantity;
        getView().enableAddToCartButton(quantity > 0);
    }

    @Override
    public void onCreateOptionMenu() {
        refreshCartCount();
    }

    private void refreshCartCount(){
        Cart cart = mAppDataManager.getCart();
        boolean showCartItemsCount = !cart.getCartItems().isEmpty();

        getView().showCartItemsCount(showCartItemsCount, String.valueOf(cart.getCartItems().size()));
    }
}
