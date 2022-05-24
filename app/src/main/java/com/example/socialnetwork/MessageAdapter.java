package com.example.socialnetwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Message> messageList = new ArrayList<Message>();
    private Context context;
    Integer ITEM_RECEIVE = 1;
    Integer ITEM_SEND = 2;

    MessageAdapter(Context context, ArrayList<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.receive, parent, false);
            return new MessageAdapter.ReceiveViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.send, parent, false);
            return new MessageAdapter.SendViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message currentMessage = messageList.get(position);
        if (Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid().equals(currentMessage.getSenderId())) {
            return ITEM_SEND;
        } else {
            return ITEM_RECEIVE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message currentMessage = messageList.get(position);
        if (holder.getClass() == SendViewHolder.class) {
            ((SendViewHolder) holder).sentMessage.setText(currentMessage.getMessage());
        } else {
            ((ReceiveViewHolder) holder).receiveMessage.setText(currentMessage.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class SendViewHolder extends RecyclerView.ViewHolder {
        public SendViewHolder(View itemView) {
            super(itemView);
        }

        TextView sentMessage = itemView.findViewById(R.id.sendMessage);
    }

    public static class ReceiveViewHolder extends RecyclerView.ViewHolder {

        public ReceiveViewHolder(View itemView) {
            super(itemView);
        }

        TextView receiveMessage = itemView.findViewById(R.id.receiveMessage);
    }
}
