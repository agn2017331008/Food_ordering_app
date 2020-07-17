package com.example.attempt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attempt.Model.Menu;
import com.example.attempt.Prevalent.Prevalent;
import com.example.attempt.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView menu_recyclerView;
    private DatabaseReference menuRef;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuRef = FirebaseDatabase.getInstance().getReference().child("Admins").child(Prevalent.currentOnlineAdmin.getAdmin_phoneNumber()).child("menu");

        menu_recyclerView = findViewById(R.id.recycler_view_menu);
        menu_recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        menu_recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Menu> options =
                new FirebaseRecyclerOptions.Builder<Menu>()
                        .setQuery(menuRef,Menu.class)
                        .build();

        FirebaseRecyclerAdapter<Menu, MenuViewHolder> adapter =
                new FirebaseRecyclerAdapter<Menu, MenuViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull final Menu model)
                    {
                        holder.txtMenuName.setText(model.getName());
                        holder.txtMenuPrice.setText("Price = "+model.getPrice()+"TK");



                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MenuActivity.this,MenuDetailsActivity.class);
                                intent.putExtra("menuid",model.getMenuid());
                                intent.putExtra("admin_phoneNumber",Prevalent.currentOnlineAdmin.getAdmin_phoneNumber());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_layout,parent,false);
                        Log.d("Home", "Home");
                        MenuViewHolder holder = new MenuViewHolder(view);
                        return  holder;
                    }
                };
        menu_recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
