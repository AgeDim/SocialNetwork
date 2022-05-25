package com.example.socialnetwork;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MailActivity extends AppCompatActivity {
    RecyclerView userView;
    ArrayList<User> users = new ArrayList<>();

    public UserAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(UserAdapter adapter) {
        this.adapter = adapter;
    }

    UserAdapter adapter;
    FirebaseAuth mAuth;
    DatabaseReference dbRef;
    EditText nameUser;
    User currentUser;
    private ImageView profilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        dbRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        userView = findViewById(R.id.userView);
        adapter = new UserAdapter(this, users);
        nameUser = findViewById(R.id.nameUser);
        profilePhoto = findViewById(R.id.profilePhoto);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this);
        userView.setLayoutManager(layoutManager);
        userView.setAdapter(adapter);
        dbRef.child("user").addValueEventListener(new ValueEventListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    User userKey = dsp.getValue(User.class);
                    String userUID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                    if (!userUID.equals(Objects.requireNonNull(userKey).getUid())) {
                        users.add(userKey);
                    }else{
                        currentUser = userKey;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            mAuth.signOut();
            finish();
            return true;
        }
        if(item.getItemId() == R.id.userPhoto){
            String name = currentUser.name;
            Intent intent = new Intent(this,ProfileActivity.class);
            intent.putExtra("name",name);
            startActivity(intent);
            return true;
        }
        return true;
    }
}