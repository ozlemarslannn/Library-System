package com.example.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    private Button btnManageRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnManageRooms = findViewById(R.id.btnManageRooms);

        btnManageRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Admin için oda yönetim ekranı açılır
                Intent intent = new Intent(AdminActivity.this, AdminRoomManagementActivity.class);
                startActivity(intent);
            }
        });
    }
}
