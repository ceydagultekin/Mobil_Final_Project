package com.example.myapplication;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private EditText etName, etSurname, etEmail, etPassword;
    private Button btnSignUp;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViewById(R.id.sign_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,LogIn.class));
            }
        });


        etName = findViewById(R.id.sign_name);
        etSurname = findViewById(R.id.sign_soy_ad);
        etEmail = findViewById(R.id.sign_email);
        etPassword = findViewById(R.id.sign_password);
        btnSignUp = findViewById(R.id.sign_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kullanıcının girdiği bilgileri al
                String name = etName.getText().toString().trim();
                String surname = etSurname.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Boş alan kontrolü yap
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUp.this, "Lütfen tüm alanları doldurun.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Firebase Authentication kullanarak email/password ile kayıt ol
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Kayıt başarılı ise buraya gelir
                                    Toast.makeText(SignUp.this, "Kayıt başarılı.", Toast.LENGTH_SHORT).show();

                                    // Firestore'a kullanıcıyı ekleyin
                                    String userId = task.getResult().getUser().getUid();
                                    addUserToFirestore(userId, name, surname, email);

                                    startActivity(new Intent(SignUp.this, LogIn.class));
                                    finish();
                                } else {
                                    // Kayıt başarısız ise buraya gelir
                                    Toast.makeText(SignUp.this, "Kayıt başarısız. Hata: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void addUserToFirestore(String userId, String name, String surname, String email) {
        // Firestore'a kullanıcıyı ekleyin
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("surname", surname);
        user.put("email", email);

        firestore.collection("users")
                .document(userId)
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Firestore'a ekleme başarılı
                            Toast.makeText(SignUp.this, "Firestore'a kullanıcı eklendi.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Firestore'a ekleme başarısız
                            Toast.makeText(SignUp.this, "Firestore'a kullanıcı eklenemedi. Hata: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
