package com.example.attempt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {


    private EditText nameEditText , phoneEditText, addressEditText , cityEditText ;

    private Button confirm_order_btn;
    private TextView totalamounttxtView;
    private  String totalAmount = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);


        totalAmount = getIntent().getStringExtra("Total Price");

        confirm_order_btn = findViewById(R.id.confirm_final_order_btn);
        nameEditText = findViewById(R.id.shipment_name);
        phoneEditText = findViewById(R.id.shipment_phone_number);
        addressEditText = findViewById(R.id.shipment_address);
        cityEditText = findViewById(R.id.shipment_city);
        totalamounttxtView = findViewById(R.id.final_total_amount);
        totalamounttxtView.setText("Total Price = " + totalAmount + "TK" );



        confirm_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  CheckShipmentDetails();
            }
        });
    }

    private void CheckShipmentDetails() {

        if(TextUtils.isEmpty(nameEditText.getText().toString()) || TextUtils.isEmpty(phoneEditText.getText().toString()) || TextUtils.isEmpty(addressEditText.getText().toString()) || TextUtils.isEmpty(cityEditText.getText().toString())){
            Toast.makeText(ConfirmFinalOrderActivity.this,"Please fill all the fields",Toast.LENGTH_LONG).show();
        }
        else{
            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {

        final  String saveCurrentDate,saveCurrentTime;

        Calendar calender = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMddyyyy");
        saveCurrentDate = currentDate.format(calender.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
        saveCurrentTime = currentTime.format(calender.getTime());

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Admins");

        HashMap<String, Object> orderMap = new HashMap<>();

        orderMap.put("TotalAmount" , totalAmount);
        orderMap.put("name",nameEditText.getText().toString());
        orderMap.put("phone",phoneEditText.getText().toString());
        orderMap.put("address",addressEditText.getText().toString());
        orderMap.put("city",cityEditText.getText().toString());
        orderMap.put("date",saveCurrentDate);
        orderMap.put("time",saveCurrentTime);
        orderMap.put("state","not shipped");
    }
}
