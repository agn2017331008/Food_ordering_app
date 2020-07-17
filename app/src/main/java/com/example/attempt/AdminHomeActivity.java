package com.example.attempt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHomeActivity extends AppCompatActivity {


   private Button profile_btn,add_menu_btn, menu_btn, setting_btn, order_btn , admin_log_out_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        profile_btn = findViewById(R.id.profile_btn);
        add_menu_btn = findViewById(R.id.add_menu_btn);
        menu_btn = findViewById(R.id.your_menu_btn);
        setting_btn = findViewById(R.id.setting_btn);
        order_btn = findViewById(R.id.order_btn);
        admin_log_out_btn = findViewById(R.id.admin_logout_btn);

        add_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, AddmenuActivity.class);
                startActivity(intent);
            }
        });

        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, AdminSettingsActivity.class));
            }
        });

        admin_log_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
