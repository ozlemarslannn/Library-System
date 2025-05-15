package com.example.library;

import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView loginSuccessText;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://libraryreservationsystem-f2a88-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");

        // XML view'ları bağlama
        editTextEmail = findViewById(R.id.emailEditText);
        editTextPassword = findViewById(R.id.passwordEditText);
        buttonLogin = findViewById(R.id.loginButton);
        loginSuccessText = findViewById(R.id.textViewLoginSuccess);

        // Giriş butonuna tıklayınca
        buttonLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase ile giriş yapma
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String uid = user.getUid();
                            checkUserRole(uid); // Kullanıcının rolünü kontrol et ve yönlendir
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkUserRole(String uid) {
        mDatabase.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String role = snapshot.child("role").getValue(String.class);
                    Log.e("LoginActivity", "User role: " + role);

                    if (role == null || role.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Role not found.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    loginSuccessText.setVisibility(View.VISIBLE);

                    // 1 saniye sonra ilgili sayfaya yönlendir
                    new Handler().postDelayed(() -> {
                        Intent intent;
                        switch (role.toLowerCase()) {
                            case "student":
                                intent = new Intent(LoginActivity.this, StudentHomeActivity.class);
                                break;
                            case "academic staff":
                                intent = new Intent(LoginActivity.this, AcademicHomeActivity.class);
                                break;
                            case "library administrator":
                                intent = new Intent(LoginActivity.this, AdminActivity.class);
                                break;
                            default:
                                Toast.makeText(LoginActivity.this, "Unknown role: " + role, Toast.LENGTH_SHORT).show();
                                return;
                        }

                        startActivity(intent);
                        finish();
                    }, 1000); // 1 saniye bekle
                } else {
                    Toast.makeText(LoginActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
