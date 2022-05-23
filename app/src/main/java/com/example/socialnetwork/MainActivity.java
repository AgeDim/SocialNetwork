package com.example.socialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email;
    EditText pass;
    Button edtLogin;
    Button redirectOnReg;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        edtLogin = findViewById(R.id.login);
        redirectOnReg = findViewById(R.id.redirectOnReg);
        error = findViewById(R.id.error);


        redirectOnReg.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        edtLogin.setOnClickListener(view -> {
            String login = email.getText().toString();
            String password = pass.getText().toString();
            login(login, password);
        });
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getApplicationContext(), "Login successful!!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, MailActivity.class);

                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Login failed!!", Toast.LENGTH_LONG).show();
            }
        });

    }
}