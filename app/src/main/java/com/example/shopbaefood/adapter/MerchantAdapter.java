package com.example.shopbaefood.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.Merchant;
import com.example.shopbaefood.ui.public_fragment.MerDetailActivity;
import com.example.shopbaefood.util.UtilApp;

import java.util.List;

public class MerchantAdapter extends RecyclerView.Adapter<MerchantAdapter.MerchantViewHolder> {
    private List<Merchant> merchantList;

    public MerchantAdapter(List<Merchant> merchantList) {
        this.merchantList = merchantList;
    }

    @NonNull
    @Override
    public MerchantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant, parent, false);
        return new MerchantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MerchantViewHolder holder, int position) {
        Merchant merchant = merchantList.get(position);
        if (merchant == null) {
            return;
        }
        holder.imgIcon.setImageResource(UtilApp.isTime(merchant.getOpenTime(),merchant.getCloseTime())?R.drawable.green_shape:R.drawable.red_shape);
        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MerDetailActivity.class);
            merchant.setStatus(UtilApp.isTime(merchant.getOpenTime(),merchant.getCloseTime())?"true":"false");
            intent.putExtra("merchant",merchant);
            view.getContext().startActivity(intent);
        });
        UtilApp.getImagePicasso(merchant.getAvatar(), holder.imgMerchant);
        holder.tvNameMerchant.setText(merchant.getName());
        holder.tvAddressMerchant.setText(merchant.getAddress());
    }

    @Override
    public int getItemCount() {
        if (merchantList != null) {
            return merchantList.size();
        }
        return 0;
    }

    public class MerchantViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMerchant;
        private TextView tvNameMerchant;
        private TextView tvAddressMerchant;
        private CardView cardView;
        private ImageView imgIcon;

        public MerchantViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_shop_icon);
            cardView = itemView.findViewById(R.id.card_merchant);
            imgMerchant = itemView.findViewById(R.id.img_merchant);
            tvNameMerchant = itemView.findViewById(R.id.tv_name_merchant);
            tvAddressMerchant = itemView.findViewById(R.id.tv_address_merchant);

        }
    }
}