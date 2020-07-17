package com.example.attempt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Register_Option_Activity extends AppCompatActivity {

    Button admin_btn, user_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__option_);

        admin_btn = findViewById(R.id.admin_register_btn);
        user_btn = findViewById(R.id.user_register_btn);

        admin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin_register();
            }
        });


        user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_register();
            }
        });
    }

    private void user_register() {

        Intent intent  = new Intent(Register_Option_Activity.this, RegisterActivity.class);

        startActivity(intent);
    }

    private void admin_register() {

        Intent intent  = new Intent(Register_Option_Activity.this, AdminRegisterActivity.class);

        startActivity(intent);
    }
}
