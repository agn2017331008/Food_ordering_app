package com.example.attempt.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attempt.Interface.ItemClickListener;
import com.example.attempt.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
   public TextView txtMenuName , txtMenuPrice;
   public ItemClickListener listener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMenuName = itemView.findViewById(R.id.menu_name);
        txtMenuPrice = itemView.findViewById(R.id.menu_price);

    }

    @Override
    public void onClick(View view) {
        listener.onClick(view,getAdapterPosition(),false);
    }


    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }
}
