package com.example.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StudentHomeActivity extends AppCompatActivity {

    private Button btnViewRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        btnViewRooms = findViewById(R.id.btnViewRooms);

        btnViewRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Burada öğrenciye özel oda listeleme sayfasına geçiş yapılabilir
                Intent intent = new Intent(StudentHomeActivity.this, RoomListActivity.class);
                intent.putExtra("role", "student");
                startActivity(intent);
            }
        });
    }
}
