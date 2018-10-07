package com.android.avanatest.products.ui.products;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.avanatest.products.R;
import com.android.avanatest.products.data.model.Product;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;


class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Product> mProducts = new ArrayList<>();

    private static final int TYPE_LOADING = 1;
    private static final int TYPE_PRODUCT = 2;
    private Listener mListener;

    public ProductsAdapter(Listener listener) {
        mListener = listener;
    }

    interface Listener {
        void onProductClick(Product product);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_LOADING) {
            return new LoadingViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_footer, viewGroup, false));
        } else {
            return new ProductViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mProducts.get(position).getType().equalsIgnoreCase("LOADING")) {
            return TYPE_LOADING;
        } else {
            return TYPE_PRODUCT;
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_PRODUCT) {
            ((ProductViewHolder) viewHolder).bind(mProducts.get(position));
        } else if (getItemViewType(position) == TYPE_LOADING) {
            ((LoadingViewHolder) viewHolder).bind();
        }
    }

    public void addItems(ArrayList<Product> resultList) {
        ArrayList<Product> newResultList = new ArrayList<>();
        newResultList.addAll(this.mProducts);
        newResultList.addAll(resultList);

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffCallback(newResultList, mProducts));
        this.mProducts.addAll(resultList);
        diffResult.dispatchUpdatesTo(this);
    }

    public void clearItems() {
        mProducts.clear();
        notifyDataSetChanged();
    }

    public void addItem(Product product) {
        mProducts.add(product);
        notifyItemInserted(mProducts.size() - 1);
    }

    public void removeItem() {
        mProducts.remove(mProducts.size() - 1);
        notifyItemRemoved(mProducts.size());
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        public TextView tvProductName, tvProductDescription;
        public ImageView ivProduct;


        public ProductViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductDescription = itemView.findViewById(R.id.tvProductDescription);
            ivProduct = itemView.findViewById(R.id.ivProduct);
        }

        public void bind(Product product) {
            itemView.setOnClickListener(view -> mListener.onProductClick(product));
            tvProductName.setText(product.getName());
            tvProductDescription.setText(product.getDescription());
            RequestOptions options =
                    RequestOptions.placeholderOf(R.drawable.ic_shopping_basket_24dp).error(R.drawable.ic_shopping_basket_24dp);
            Glide.with(itemView.getContext()).applyDefaultRequestOptions(options).load(product.getImageUrl()).into(ivProduct);
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(View itemView) {
            super(itemView);
        }

        public void bind() {
        }
    }

    private class MyDiffCallback extends DiffUtil.Callback {


        private final ArrayList<Product> newProducts;
        private final ArrayList<Product> oldProducts;

        public MyDiffCallback(ArrayList<Product> newProducts, ArrayList<Product> oldProducts) {

            this.newProducts = newProducts;
            this.oldProducts = oldProducts;
        }

        @Override
        public int getOldListSize() {
            return oldProducts.size();
        }

        @Override
        public int getNewListSize() {
            return newProducts.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldProducts.get(oldItemPosition).getId().equals(newProducts.get(newItemPosition).getId());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldProducts.get(oldItemPosition).getId().equals(newProducts.get(newItemPosition).getId());
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }
}