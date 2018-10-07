package com.android.avanatest.products.ui.shoppingcart;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.avanatest.products.R;
import com.android.avanatest.products.data.model.CartItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;


class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.CartItemViewHolder> {

    private ArrayList<CartItem> mCartItems = new ArrayList<>();

    private Listener mListener;

    public CartItemsAdapter(Listener listener) {
        mListener = listener;
    }

    interface Listener {
        void onCartItemClick(CartItem cartItem);
    }


    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new CartItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cart, viewGroup, false));
    }

    @Override
    public int getItemCount() {
        return mCartItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder viewHolder, int position) {
        viewHolder.bind(mCartItems.get(position));
    }

    public void addCartItems(ArrayList<CartItem> items) {
        mCartItems = items;
        notifyDataSetChanged();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder {

        public TextView tvProductName, tvCartItemPrice, tvProductItemQuantity;
        public ImageView ivProduct;


        public CartItemViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvCartItemPrice = itemView.findViewById(R.id.tvTotalCartItemPrice);
            tvProductItemQuantity = itemView.findViewById(R.id.tvQuantity);
            ivProduct = itemView.findViewById(R.id.ivProduct);
        }

        public void bind(CartItem cartItem) {
            itemView.setOnClickListener(view -> mListener.onCartItemClick(cartItem));
            tvProductName.setText(cartItem.getProduct().getName());
            tvCartItemPrice.setText(cartItem.getPriceWithCurrency());
            tvProductItemQuantity.setText(String.format("%s", cartItem.getQuantity()));
            RequestOptions options =
                    RequestOptions.placeholderOf(R.drawable.ic_shopping_basket_24dp).error(R.drawable.ic_shopping_basket_24dp);
            Glide.with(itemView.getContext()).applyDefaultRequestOptions(options).load(cartItem.getProduct().getImageUrl()).into(ivProduct);
        }
    }
}