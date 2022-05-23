package com.example.socialnetwork;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText emailReg;
    EditText passReg;
    Button reg;
    TextView error1;
    EditText name;
    DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_register);
        emailReg = findViewById(R.id.emailReg);
        passReg = findViewById(R.id.passReg);
        reg = findViewById(R.id.reg);
        error1 = findViewById(R.id.error1);
        name = findViewById(R.id.name);
        reg.setOnClickListener(view -> {
            String login = emailReg.getText().toString();
            String password = passReg.getText().toString();
            register(name.getText().toString(), login, password);
        });
    }

    private void register(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    addUserToDB(name, email, mAuth.getCurrentUser().getUid());
                    Toast.makeText(RegisterActivity.this, "Registartion successfully!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, MailActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registartion failed!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void addUserToDB(String name, String email, String uid) {
        mDataBase = FirebaseDatabase.getInstance().getReference();
        mDataBase.child("user").child(uid).setValue(new User(name,email,uid));
    }

}
