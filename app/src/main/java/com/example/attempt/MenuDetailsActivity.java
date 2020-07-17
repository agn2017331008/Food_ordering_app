package com.example.attempt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.attempt.Model.Menu;
import com.example.attempt.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MenuDetailsActivity extends AppCompatActivity {

    private TextView menu_name_txt_view, menu_price_txt_view, menu_description_txt_view;
    private ElegantNumberButton quantity_number_btn;
    private Button add_to_car_btn;
    private String menuID = "",current_admin_phone_number="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_details);

        menuID = getIntent().getStringExtra("menuid");
        current_admin_phone_number = getIntent().getStringExtra("admin_phoneNumber");

        menu_description_txt_view  = findViewById(R.id.menu_description_details);
        menu_name_txt_view = findViewById(R.id.menu_name_details);
        menu_price_txt_view = findViewById(R.id.menu_price_details);

        quantity_number_btn = findViewById(R.id.number_btn);
        add_to_car_btn = findViewById(R.id.menu_add_to_cart_button);



        getMenuDetails(menuID,current_admin_phone_number);

        add_to_car_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();
            }
        });
    }

    private void addingToCartList() {

        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM:dd:yyyy");
        saveCurrentDate =currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference adminOrderListRef = FirebaseDatabase.getInstance().getReference().child("Admins").child(current_admin_phone_number).child("Order");
        final  DatabaseReference userCartListRef =   FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhoneNumber()).child("Cart");



        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("Restaurant_Phone_number",current_admin_phone_number);
        cartMap.put("menuid",menuID);
        cartMap.put("name", menu_name_txt_view.getText().toString());
        cartMap.put("price",menu_price_txt_view.getText().toString());
        cartMap.put("order_date",saveCurrentDate);
        cartMap.put("order_time",saveCurrentTime);
        cartMap.put("quantity",quantity_number_btn.getNumber());

//        final HashMap<String,Object> orderMap = new HashMap<>();
//
//        orderMap.put("Customer_Phone_number",Prevalent.currentOnlineUser.getPhoneNumber());
//        orderMap.put("menuid",menuID);
//        orderMap.put("name", menu_name_txt_view.getText().toString());
//        orderMap.put("price",menu_price_txt_view.getText().toString());
//        orderMap.put("order_date",saveCurrentDate);
//        orderMap.put("order_time",saveCurrentTime);
//        orderMap.put("quantity",quantity_number_btn.getNumber());

        userCartListRef.child(menuID).updateChildren(cartMap)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if( task.isSuccessful()){

                    Toast.makeText(MenuDetailsActivity.this,"Added to cart.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MenuDetailsActivity.this,HomeActivity.class));

                }
            }
        });

    }

    private void getMenuDetails(String menuID,String current_admin_phone_number) {

        DatabaseReference MenuRef = FirebaseDatabase.getInstance().getReference().child("Admins").child(current_admin_phone_number).child("menu");

        MenuRef.child(menuID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    Menu menu = dataSnapshot.getValue(Menu.class);

                    menu_name_txt_view.setText(menu.getName());
                    menu_price_txt_view.setText(menu.getPrice());
                    menu_description_txt_view.setText(menu.getDescription());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
