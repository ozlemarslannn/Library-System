package com.example.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AcademicHomeActivity extends AppCompatActivity {

    private Button btnViewAcademicRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_home);

        btnViewAcademicRooms = findViewById(R.id.btnViewAcademicRooms);

        btnViewAcademicRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Akademik personele özel oda listeleme sayfası açılır
                Intent intent = new Intent(AcademicHomeActivity.this, RoomListActivity.class);
                intent.putExtra("role", "academic");
                startActivity(intent);
            }
        });
    }
}
