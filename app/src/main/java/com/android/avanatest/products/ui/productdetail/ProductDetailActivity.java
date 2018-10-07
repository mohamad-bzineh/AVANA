package com.android.avanatest.products.ui.productdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.avanatest.products.R;
import com.android.avanatest.products.data.model.Product;
import com.android.avanatest.products.ui.base.BaseMvpActivity;
import com.android.avanatest.products.ui.base.BasePresenter;
import com.android.avanatest.products.ui.shoppingcart.ShoppingCartActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;


public class ProductDetailActivity extends BaseMvpActivity implements ProductDetailContracts.View {

    private static final String EXTRA_PRODUCT = "extra_product";

    public static void startActivity(Context context, Product product) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_PRODUCT, product);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Inject
    ProductDetailContracts.Presenter<ProductDetailContracts.View> mPresenter;

    TextView tvProductName, tvProductPrice, tvProductDescription, tvCartItemsCount;
    ImageView ivProductImage;
    Button btnCheckout;
    ElegantNumberButton enbProductCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getActivityComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
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
        Product product = extras.getParcelable(EXTRA_PRODUCT);
        mPresenter.initProduct(product);
    }

    private void setupLayout() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.title_products));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductDescription = findViewById(R.id.tvProductDescription);
        ivProductImage = findViewById(R.id.ivProductBackground);
        btnCheckout = findViewById(R.id.btnAddToCart);
        enbProductCount = findViewById(R.id.enbProductCount);
        btnCheckout.setOnClickListener(view -> mPresenter.onAddToCartClick());
        enbProductCount.setOnValueChangeListener((view, oldValue, newValue) -> {
            mPresenter.onProductQuantityChange(newValue);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping_cart, menu);
        final MenuItem menuItem = menu.findItem(R.id.item_shopping_cart);
        View actionView = menuItem.getActionView();
        tvCartItemsCount = actionView.findViewById(R.id.tvCartItemsCount);
        mPresenter.onCreateOptionMenu();
        actionView.setOnClickListener(v -> onOptionsItemSelected(menuItem));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_shopping_cart: {
                startActivity(new Intent(this, ShoppingCartActivity.class));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProductInfo(Product product) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(product.getName());
        }
        tvProductName.setText(product.getName());
        tvProductPrice.setText(product.getPriceWithCurrency());
        tvProductDescription.setText(product.getDescription());
        RequestOptions options = new RequestOptions().fitCenter()
                .placeholder(R.drawable.ic_shopping_basket_24dp)
                .error(R.drawable.ic_shopping_basket_24dp);
        Glide.with(this).load(product.getImageUrl()).apply(options).into(ivProductImage);
    }

    @Override
    public void showCartItemsCount(boolean show, String count) {
        if (tvCartItemsCount == null) return;
        tvCartItemsCount.setVisibility(show ? View.VISIBLE : View.GONE);
        tvCartItemsCount.setText(count);
    }

    @Override
    public void enableAddToCartButton(boolean enable) {
        btnCheckout.setEnabled(enable);
    }

    @Override
    public void showCartItemInfo(int quantity) {
        enbProductCount.setNumber(String.valueOf(quantity));
    }
}
