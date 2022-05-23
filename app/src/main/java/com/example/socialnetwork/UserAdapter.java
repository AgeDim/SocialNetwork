package com.example.socialnetwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private final ArrayList<User> users;
    private final Context context;

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
