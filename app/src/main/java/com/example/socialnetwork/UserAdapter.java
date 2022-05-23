package com.example.socialnetwork;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.*;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private ArrayList<User> users = new ArrayList<User>();
    private final Context context;

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    UserAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_of_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        User currentUser = users.get(position);
        holder.setName(currentUser.name);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, chatActivity.class);
            intent.putExtra("name",currentUser.name);
            intent.putExtra("uid", Objects.requireNonNull(currentUser.getUid()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        View rview;

        public UserViewHolder(View itemView) {
            super(itemView);
            rview = itemView;
        }

        public void setName(String name) {
            TextView userName = rview.findViewById(R.id.nameOfUser);
            userName.setText(name);
        }

    }


}
