package com.example.socialnetwork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    ImageView profilePhoto;
    Button choosePhoto, uploadPhoto, nameEdit;
    EditText nameUser;
    ImageButton back;
    ProgressBar bar;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String oldName;
    Uri imagePath;
    FirebaseStorage storage;
    StorageReference reference;
    Integer PICK_IMAGE_REQUEST = 71;
    DatabaseReference mDB = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.getData() == null) {
                return;
            }
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
                profilePhoto.setBackground(ob);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        storage = FirebaseStorage.getInstance();
        reference = FirebaseStorage.getInstance().getReference();
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
        choosePhoto.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });
        uploadPhoto.setOnClickListener(v -> {
            if (imagePath != null) {
                StorageReference ref = reference.child("myImages/" + mAuth.getCurrentUser().getUid());
                UploadTask uploadTask = ref.putFile(imagePath);
                mDB.child("user").addValueEventListener(new ValueEventListener() {

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dsp : snapshot.getChildren()) {
                            User userKey = dsp.getValue(User.class);
                            String userUID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            if (userUID.equals(Objects.requireNonNull(userKey).getUid())) {
                                mDB.child("user").child(userKey.getUid()).child("imagePath").setValue(mAuth.getCurrentUser().getUid());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } else {
                Toast.makeText(this, "Please choose an Image", Toast.LENGTH_SHORT).show();
            }
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