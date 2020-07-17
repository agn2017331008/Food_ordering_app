package com.example.attempt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attempt.Model.Admins;
import com.example.attempt.Model.Users;
import com.example.attempt.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {


    private Button LoginBtn;
    private EditText InputNumber, InputPassword;

    private TextView admin_link, not_admin_link;
    private String parentDbName = "Users";
    private CheckBox checkBox_remember_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginBtn = (Button) findViewById(R.id.log_in_btn2);

        InputPassword = (EditText) findViewById(R.id.log_in_password);
        InputNumber = (EditText) findViewById(R.id.log_in_phone_number);
        checkBox_remember_me = (CheckBox) findViewById(R.id.remember_me_chkb);


        admin_link = findViewById(R.id.admin_panel_link);
        not_admin_link = findViewById(R.id.non_admin_panel_link);


        Paper.init(this);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });


        admin_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginBtn.setText("Admin Log in");
                admin_link.setVisibility(View.INVISIBLE);
                not_admin_link.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
                checkBox_remember_me.setVisibility(View.INVISIBLE);
            }
        });

        not_admin_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginBtn.setText("Log in");
                admin_link.setVisibility(View.VISIBLE);
                not_admin_link.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
                checkBox_remember_me.setVisibility(View.VISIBLE);
            }
        });
    }

    private void LoginUser() {


        String phone_number = InputNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(phone_number)) {
            Toast.makeText(Login.this, "Please fill up all the fields.", Toast.LENGTH_SHORT).show();
        } else {

            AllowAccessToAccount(phone_number, password);
        }


    }

    private void AllowAccessToAccount(final String phone_number, final String password) {


        if (checkBox_remember_me.isChecked()) {

            Paper.book().write(Prevalent.userPhone, phone_number);
            Paper.book().write(Prevalent.userPassword, password);

        }
        final DatabaseReference RootRef;


        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone_number).exists()) {


                    if (parentDbName.equals("Users")) {
                        Users userdata = dataSnapshot.child(parentDbName).child(phone_number).getValue(Users.class);

                        if (userdata.getPhoneNumber().equals(phone_number)) {
                            if (userdata.getPassword().equals(password)) {


                                Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Login.this, HomeActivity.class);

                                Prevalent.currentOnlineUser = userdata;
                                startActivity(intent);

                            }
                        }
                    }
                    else if (parentDbName.equals("Admins"))
                    {
                        Admins admindata = dataSnapshot.child(parentDbName).child(phone_number).getValue(Admins.class);

                        if (admindata.getAdmin_phoneNumber().equals(phone_number)) {
                            if (admindata.getAdmin_password().equals(password)) {


                                Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Login.this, AdminHomeActivity.class);

                                Prevalent.currentOnlineAdmin = admindata;
                                startActivity(intent);

                            }
                        }
                    }
                } else {
                    Toast.makeText(Login.this, "Your account doesn't exist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
