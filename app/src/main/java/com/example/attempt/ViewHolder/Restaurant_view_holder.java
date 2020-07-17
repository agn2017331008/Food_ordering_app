package com.example.attempt.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attempt.Interface.ItemClickListener;
import com.example.attempt.R;

public class Restaurant_view_holder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txtResName;
    public ImageView imageView;
    public ItemClickListener itemClickListener;


    public Restaurant_view_holder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.restaurant_image);
        txtResName = itemView.findViewById(R.id.restaurant_name);
    }

    public  void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}


