package com.example.android52_day7;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    private static final String TAG = "ProductAdapter";
    private ArrayList<ProductModel> mListData;
    private Context mContext;
    private IItemClickListener mCallback;

    public ProductAdapter(ArrayList<ProductModel> listData) {
        this.mListData = listData;
    }

    public void setCallback(IItemClickListener callback) {
        this.mCallback = callback;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);
        ProductModel productModel = mListData.get(position);

        holder.tvProductName.setText(productModel.getProductName());
        holder.tvPrices.setText(productModel.getProductPrices());
        holder.tvRating.setText(productModel.getRate());

        if (productModel.isWish()){
            Glide.with(mContext).load(R.drawable.path).into(holder.imgWishlist);
        }else {
            Glide.with(mContext).load(R.drawable.wish).into(holder.imgWishlist);
        }

        Glide.with(mContext).load(productModel.getProductImage()).into(holder.imgProduct);
//        holder.llProductItemMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCallback.onItemClick(position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mListData != null ? mListData.size() : 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvProductName, tvPrices, tvRating;
        ImageView imgProduct, imgWishlist, imgRemove;
        CardView llProductItemMain;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrices = itemView.findViewById(R.id.tvPrices);
            tvRating = itemView.findViewById(R.id.tvRating);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            imgWishlist = itemView.findViewById(R.id.imgWishlist);
            imgRemove = itemView.findViewById(R.id.imgRemove);
            llProductItemMain = itemView.findViewById(R.id.llProductItemMain);

            llProductItemMain.setOnClickListener(this);
            imgRemove.setOnClickListener(this);
            imgWishlist.setOnClickListener(this);
            tvProductName.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.llProductItemMain:
                    int position = getAdapterPosition();
                    if (mCallback != null) {
                        mCallback.onItemClick(position);
                    }
                    break;

                case R.id.imgRemove:
                    mCallback.onDelete(getAdapterPosition());
                    break;
                case R.id.imgWishlist:
                    mCallback.onChangeWishList(getAdapterPosition());
                    break;
                case R.id.tvProductName:
                    mCallback.onUpdate(getAdapterPosition());
                    break;
            }
        }
    }
}
