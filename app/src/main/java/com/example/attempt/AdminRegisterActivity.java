package com.example.attempt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminRegisterActivity extends AppCompatActivity {


    EditText name, phone , password;
    private Button register_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        name = findViewById(R.id.admin_register_name_input);
        phone = findViewById(R.id.admin_register_phone_number_input);
        password = findViewById(R.id.admin_register_password_input);


        register_btn = findViewById(R.id.admin_register_btn);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_admin_account();
            }
        });
    }

    private void create_admin_account() {
        String admin_name = name.getText().toString();
        String admin_phone_number =phone.getText().toString();
        String admin_password = password.getText().toString();

        if (TextUtils.isEmpty(admin_name) || TextUtils.isEmpty(admin_phone_number) || TextUtils.isEmpty(admin_password)) {
            Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
        } else {

            ValidatePhoneNumber(admin_name, admin_phone_number,admin_password);
        }
    }

    private void ValidatePhoneNumber(final  String admin_name, final String admin_phone_number, final String admin_password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.child("Admins").child(admin_phone_number).exists()){

                    HashMap<String,Object> userdataMap = new HashMap<>();
                    userdataMap.put("admin_name",admin_name);
                    userdataMap.put("admin_phoneNumber",admin_phone_number);
                    userdataMap.put("admin_password",admin_password);

                    RootRef.child("Admins").child(admin_phone_number).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {
                                                           if(task.isSuccessful()){
                                                               Toast.makeText(AdminRegisterActivity.this,"Congratulations! your account has been created!",Toast.LENGTH_SHORT).show();


                                                               Intent intent = new Intent(AdminRegisterActivity.this,  MainActivity.class);
                                                               startActivity(intent);
                                                           }
                                                           else{

                                                               Toast.makeText(AdminRegisterActivity.this,"Network Error! Please try again.",Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                   }

                            );
                }
                else{
                    Toast.makeText(AdminRegisterActivity.this,"Your phone number already exists",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminRegisterActivity.this,  MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
