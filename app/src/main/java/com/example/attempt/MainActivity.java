package com.example.attempt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.attempt.Model.Users;
import com.example.attempt.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {


    Button signup_btn,log_in_btn_join;
    String parentDbName = "Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup_btn = (Button) findViewById(R.id.sign_up_btn);
        log_in_btn_join = (Button)findViewById(R.id.log_in_btn_join);

        Paper.init(this);

        String phone = Paper.book().read(Prevalent.userPhone);
        String password = Paper.book().read(Prevalent.userPassword);


        if(phone != "" && password != ""){
            if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)){

                AllowAccess(phone, password);
            }
        }
        log_in_btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register_Option_Activity.class);
                startActivity(intent);
            }
        });


    }

    private void AllowAccess(final String phone_number, final  String password) {
        final DatabaseReference RootRef;

        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(phone_number).exists()){

                    Users userdata = dataSnapshot.child(parentDbName).child(phone_number).getValue(Users.class);

                    if(userdata.getPhoneNumber().equals(phone_number)){
                        if(userdata.getPassword().equals(password)){
                            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                           Prevalent.currentOnlineUser = userdata;
                            startActivity(intent);
                        }
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Your account doesn't exist!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
