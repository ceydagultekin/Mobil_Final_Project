package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Giris extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        firebaseAuth = FirebaseAuth.getInstance();

        // Kullanıcı oturum açık mı kontrol et
        if (firebaseAuth.getCurrentUser() != null) {
            // Kullanıcı oturum açık ise direkt MainActivity'e yönlendir
            startActivity(new Intent(Giris.this, MainActivity.class));
            finish(); // Giriş ekranını kapat
        }

        findViewById(R.id.buttonlogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Giris.this,LogIn.class));
            }
        });

        findViewById(R.id.buttonsignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Giris.this,SignUp.class));
            }
        });


    }
}