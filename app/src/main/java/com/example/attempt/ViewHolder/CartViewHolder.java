package com.example.attempt.ViewHolder;

import android.content.ClipData;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attempt.Interface.ItemClickListener;
import com.example.attempt.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView cart_menu_name_txtView , cart_menu_price_txtView, cart_menu_quantity_txtView;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        cart_menu_name_txtView = itemView.findViewById(R.id.cart_menu_name);
        cart_menu_price_txtView = itemView.findViewById(R.id.cart_menu_price);
        cart_menu_quantity_txtView = itemView.findViewById(R.id.cart_menu_quantity);
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
