package com.android.avanatest.products.ui.products;

import com.android.avanatest.products.data.interfaces.IDataManager;
import com.android.avanatest.products.data.model.Cart;
import com.android.avanatest.products.data.model.Product;
import com.android.avanatest.products.ui.base.BaseMvpPresenter;
import com.android.avanatest.products.ui.base.ErrorView;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class ProductsPresenter extends BaseMvpPresenter<ProductsContracts.View> implements ProductsContracts.Presenter<ProductsContracts.View> {

    private ArrayList<Product> mProducts = new ArrayList<Product>();

    private int page = 1;


    @Inject
    public ProductsPresenter(IDataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void onAttachView(ProductsContracts.View view) {
        super.onAttachView(view);
        fetchProducts();
    }

    @Override
    public void onRefreshProductsClick() {
        fetchProducts();
    }

    @Override
    public void onProductClick(Product product) {
        getView().openProductDetailScreen(product);
    }

    @Override
    public void onCartFabClick() {
        if (mAppDataManager.getCart().getCartItems().isEmpty()) {
            getView().showError("Cart is empty", ErrorView.ERROR_TOAST);
        } else {
            getView().showCartScreen();
        }
    }

    @Override
    public void getProducts() {
        fetchProducts();
    }

    private void fetchProducts() {
        getView().showProgress();
        Disposable disposable = mAppDataManager.getProducts(page)
                .subscribe(products -> {
                    if (!products.isEmpty()) {
                        mProducts = products;
                        ++page;
                        getView().showProducts(products);
                    } else {
                        getView().removeLoadingMore();
                    }
                    getView().hideProgress();
                }, error -> {
                    handleApiError(error, ErrorView.ERROR_DIALOG);
                    getView().hideProgress();
                });
        addToSubscription(disposable);
    }

    @Override
    public void onResume() {
        super.onResume();
        Cart cart = mAppDataManager.getCart();
        boolean showCartItemsCount = !cart.getCartItems().isEmpty();

        getView().showCartItemsCount(showCartItemsCount, String.valueOf(cart.getCartItems().size()));
    }
}
