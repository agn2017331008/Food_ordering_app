package com.example.attempt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class RegisterActivity extends AppCompatActivity {


    EditText register_name, register_phone_no, register_password;
    Button register_btn;

    ProgressDialog loading_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_btn = findViewById(R.id.register_btn);

        register_name = findViewById(R.id.register_name_input);
        register_phone_no = findViewById(R.id.register_phone_number_input);
        register_password = findViewById(R.id.register_password_input);


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

    }

    private void createAccount() {

        String name = register_name.getText().toString();
        String phone_number = register_phone_no.getText().toString();
        String password = register_password.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone_number) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
        } else {

            ValidatePhoneNumber(name, phone_number, password);
        }
    }

    private void ValidatePhoneNumber(final String name, final String phone_number, final String password) {


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.child("Users").child(phone_number).exists()){

                    HashMap<String,Object> userdataMap = new HashMap<>();
                    userdataMap.put("name",name);
                    userdataMap.put("phoneNumber",phone_number);
                    userdataMap.put("password",password);

                    RootRef.child("Users").child(phone_number).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {
                                                           if(task.isSuccessful()){
                                                               Toast.makeText(RegisterActivity.this,"Congratulations! your account has been created!",Toast.LENGTH_SHORT).show();


                                                               Intent intent = new Intent(RegisterActivity.this,  Login.class);
                                                               startActivity(intent);
                                                           }
                                                           else{

                                                               Toast.makeText(RegisterActivity.this,"Network Error! Please try again.",Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                   }

                            );
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Your phone number already exists",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,  MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
