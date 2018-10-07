package com.android.avanatest.products.ui.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.android.avanatest.products.R;
import com.android.avanatest.products.data.model.CartItem;
import com.android.avanatest.products.ui.base.BaseMvpActivity;
import com.android.avanatest.products.ui.base.BasePresenter;
import com.android.avanatest.products.ui.checkout.CheckoutActivity;
import com.android.avanatest.products.ui.productdetail.ProductDetailActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

public class ShoppingCartActivity extends BaseMvpActivity implements ShoppingCartContracts.View {

    @Inject
    ShoppingCartContracts.Presenter<ShoppingCartContracts.View> mPresenter;

    TextView tvCartTotalPrice;
    Button btnCheckout;

    RecyclerView rvCartITems;
    private CartItemsAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getActivityComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.title_shopping_cart));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        tvCartTotalPrice = findViewById(R.id.tvProductPrice);
        btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(view -> mPresenter.onCheckoutClick());
        setupAdapter();
    }

    private void setupAdapter() {
        rvCartITems = findViewById(R.id.rvCartItems);
        linearLayoutManager = new LinearLayoutManager(this);
        rvCartITems.setLayoutManager(linearLayoutManager);
        mAdapter = new CartItemsAdapter(product -> {
            ProductDetailActivity.startActivity(this, product.getProduct());
        });
        rvCartITems.setAdapter(mAdapter);
    }

    @Override
    public void showOrderDetails(String totalPrice) {
        tvCartTotalPrice.setText(totalPrice);
    }

    @Override
    public void showCartItems(ArrayList<CartItem> cartItems) {
        mAdapter.addCartItems(cartItems);
    }

    @Override
    public void openCheckoutScreen() {
        startActivity(new Intent(this, CheckoutActivity.class));
    }
}
