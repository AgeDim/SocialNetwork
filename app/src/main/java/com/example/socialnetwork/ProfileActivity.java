package com.example.socialnetwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    ImageView profilePhoto;
    Button choosePhoto, uploadPhoto, nameEdit;
    EditText nameUser;
    ImageButton back;
    ProgressBar bar;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String oldName;
    DatabaseReference mDB = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameUser = findViewById(R.id.nameUser);
        back = findViewById(R.id.back);
        nameEdit = findViewById(R.id.nameEdit);
        choosePhoto = findViewById(R.id.choosePhoto);
        uploadPhoto = findViewById(R.id.uploadPhoto);
        profilePhoto = findViewById(R.id.profilePhoto);
        bar = findViewById(R.id.bar);
        back.setOnClickListener(v -> {
            finish();
        });
        oldName = getIntent().getStringExtra("name");
        nameUser.setHint(oldName);
        nameEdit.setEnabled(true);
        nameEdit.setOnClickListener(v -> {
            String newName;
            newName = nameUser.getText().toString();
            mDB.child("user").addValueEventListener(new ValueEventListener() {

                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dsp : snapshot.getChildren()) {
                        User userKey = dsp.getValue(User.class);
                        if (Objects.requireNonNull(userKey).name.equals(oldName)) {
                            mDB.child("user").child(userKey.getUid()).child("name").setValue(newName);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            nameEdit.setEnabled(false);
        });
    }
}