package com.example.socialnetwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class chatActivity extends AppCompatActivity {
    private RecyclerView chatView;
    private EditText messageBox;
    private ImageView send;
    private MessageAdapter messageAdapter;
    private ArrayList<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String uid = intent.getStringExtra("uid");
        Objects.requireNonNull(getSupportActionBar()).setTitle(name);
        chatView = findViewById(R.id.chatView);
        messageBox = findViewById(R.id.messageBox);
        send = findViewById(R.id.send);
        messageList = new ArrayList<Message>();
        messageAdapter = new MessageAdapter(this, messageList);
        send.setOnClickListener(v -> {

            Toast.makeText(chatActivity.this, "ХРЮ ебать!", Toast.LENGTH_LONG).show();
        });
    }
}