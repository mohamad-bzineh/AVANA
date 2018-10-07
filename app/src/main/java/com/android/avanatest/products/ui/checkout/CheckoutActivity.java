package com.android.avanatest.products.ui.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.avanatest.products.R;
import com.android.avanatest.products.ui.base.BaseMvpActivity;
import com.android.avanatest.products.ui.base.BasePresenter;
import com.android.avanatest.products.ui.products.ProductsActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;


public class CheckoutActivity extends BaseMvpActivity implements CheckoutContracts.View {

    @Inject
    CheckoutContracts.Presenter<CheckoutContracts.View> mPresenter;

    TextView tvTotalPrice, tvUserName, tvEmail, tvCardLast4, tvCardType, tvCartItemsCount;
    Button btnPayNow;
    EditText etAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getActivityComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
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
            getSupportActionBar().setTitle(getString(R.string.title_products));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        tvTotalPrice = findViewById(R.id.tvOrderPrice);
        tvEmail = findViewById(R.id.tvEmail);
        tvUserName = findViewById(R.id.tvName);
        tvCardLast4 = findViewById(R.id.tvCardLast4);
        tvCardType = findViewById(R.id.tvCardType);
        btnPayNow = findViewById(R.id.btnPayNow);
        btnPayNow.setOnClickListener(v -> {
            mPresenter.onPayButtonClick(etAddress.getText().toString());
        });
        etAddress = findViewById(R.id.etAddress);
        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etAddress.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    public void showOrderSummary(String totalPayment, String totalItems, String name, String email, String lastCreditNumber, String cardType) {
        tvTotalPrice.setText(totalPayment);
        tvUserName.setText(name);
        tvEmail.setText(email);
        tvCardLast4.setText(lastCreditNumber);
        tvCardType.setText(cardType);
    }


    @Override
    public void showMissingAddress() {
        etAddress.setError("You need to add your address");
    }

    @Override
    public void showPaymentSuccess() {
        new AlertDialog.Builder(this)
                .setTitle("Payment Success")
                .setMessage("Payment was made successfully, Continue shopping")
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, (dialog, whichButton) -> {
                    mPresenter.onContinueShoppingClick();
                }).show();
    }

    @Override
    public void openProductsScreen() {
        Intent intent = new Intent(this, ProductsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
