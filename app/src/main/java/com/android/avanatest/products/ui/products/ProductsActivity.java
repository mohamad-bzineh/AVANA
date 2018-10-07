package com.android.avanatest.products.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.avanatest.products.R;
import com.android.avanatest.products.data.model.Product;
import com.android.avanatest.products.ui.base.BaseMvpActivity;
import com.android.avanatest.products.ui.base.BasePresenter;
import com.android.avanatest.products.ui.shoppingcart.ShoppingCartActivity;
import com.android.avanatest.products.ui.productdetail.ProductDetailActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

public class ProductsActivity extends BaseMvpActivity implements ProductsContracts.View {

    @Inject
    ProductsContracts.Presenter<ProductsContracts.View> mPresenter;

    private ProductsAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    boolean isLoading = false;

    TextView tvCartItemsCount;
    RecyclerView rvProducts;
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getActivityComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        setupLayout();
        mPresenter.onAttachView(this);
    }

    @NotNull
    @Override
    protected BasePresenter<?> getPresenter() {
        return mPresenter;
    }

    @Override
    protected void sendExtrasToPresenter(@NotNull Bundle extras) {

    }

    private void setupLayout() {
        rvProducts = findViewById(R.id.rvProducts);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        tvCartItemsCount = findViewById(R.id.tvCartItemsCount);

        getSupportActionBar().setTitle(getString(R.string.title_products));

        FloatingActionButton fab = findViewById(R.id.fabCart);
        fab.setOnClickListener(view -> {
            mPresenter.onCartFabClick();
        });

        linearLayoutManager = new LinearLayoutManager(this);
        rvProducts.setLayoutManager(linearLayoutManager);
        mAdapter = new ProductsAdapter(product -> mPresenter.onProductClick(product));
        rvProducts.setAdapter(mAdapter);

        swipeRefresh.setOnRefreshListener(() -> mPresenter.onRefreshProductsClick());

        rvProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (visibleItemCount + pastVisibleItems >= totalItemCount && !isLoading) {
                    Product product = new Product();
                    product.setType("LOADING");
                    mAdapter.addItem(product);
                    mPresenter.getProducts();
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public void removeLoadingMore() {
        mAdapter.removeItem();
    }

    @Override
    public void showCartScreen() {
        startActivity(new Intent(this, ShoppingCartActivity.class));
    }

    @Override
    public void showProducts(ArrayList<Product> products) {
        if (mAdapter.getItemCount() > 0)
            mAdapter.removeItem();
        if (products.size() > 0) {
            mAdapter.addItems(products);
            isLoading = false;
        }
    }

    @Override
    public void showCartItemsCount(boolean showCartItemsCount, String count) {
        tvCartItemsCount.setVisibility(showCartItemsCount ? View.VISIBLE : View.GONE);
        tvCartItemsCount.setText(count);
    }

    @Override
    public void showEmptyProducts() {

    }

    @Override
    public void openProductDetailScreen(Product product) {
        ProductDetailActivity.startActivity(this, product);
    }

    @Override
    public void showProgress() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);
    }
}
