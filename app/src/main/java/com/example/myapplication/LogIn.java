package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnSignUp;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etEmail = findViewById(R.id.login_email);
        etPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.log_login);
        btnSignUp = findViewById(R.id.log_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        findViewById(R.id.log_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,SignUp.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kullanıcının girdiği bilgileri al
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Boş alan kontrolü yap
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LogIn.this, "Lütfen tüm alanları doldurun.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Firebase Authentication kullanarak giriş yap
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Giriş başarılı ise buraya gelir
                                    Toast.makeText(LogIn.this, "Giriş başarılı.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LogIn.this, MainActivity.class));
                                    finish();
                                } else {
                                    // Giriş başarısız ise buraya gelir
                                    Toast.makeText(LogIn.this, "Giriş başarısız. Hata: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}
