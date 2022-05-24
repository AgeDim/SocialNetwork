package com.example.socialnetwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class chatActivity extends AppCompatActivity {
    private RecyclerView chatView;
    private EditText messageBox;
    private ImageView send;
    private MessageAdapter messageAdapter;
    ArrayList<Message> messageList = new ArrayList<Message>();
    private ImageButton photoBtn;
    private DatabaseReference mDB;
    String receiverRoom;
    String senderRoom;
    String senderUID;
    String name;
    String receiverUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        photoBtn = findViewById(R.id.photo);
        name = intent.getStringExtra("name");
        receiverUID = intent.getStringExtra("uid");
        senderUID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        senderRoom = receiverUID + senderUID;
        receiverRoom = senderUID + receiverUID;
        Objects.requireNonNull(getSupportActionBar()).setTitle(name);
        chatView = findViewById(R.id.chatView);
        messageBox = findViewById(R.id.messageBox);
        send = findViewById(R.id.send);
        messageAdapter = new MessageAdapter(this, messageList);
        mDB = FirebaseDatabase.getInstance().getReference();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this);
        chatView.setLayoutManager(layoutManager);
        chatView.setAdapter(messageAdapter);
        photoBtn.setOnClickListener(v -> {
            Toast.makeText(this, "должно отправлять фотки но не отправляет", Toast.LENGTH_SHORT).show();
        });
        send.setOnClickListener(v -> {
            String message = messageBox.getText().toString();
            Message messageObj = new Message(message, senderUID);
            mDB.child("chats").child(senderRoom).child("message").push().setValue(messageObj).addOnSuccessListener(unused -> {
                mDB.child("chats").child(receiverRoom).child("message").push().setValue(messageObj);

                messageBox.setText("");
            });
        });

        mDB.child("chats").child(senderRoom).child("message").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Message messages = ds.getValue(Message.class);
                    messageList.add(messages);
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}