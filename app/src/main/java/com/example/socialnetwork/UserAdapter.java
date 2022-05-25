package com.example.socialnetwork;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.*;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private ArrayList<User> users = new ArrayList<User>();
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
        try {
            holder.setImage(currentUser.uid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, chatActivity.class);
            intent.putExtra("name", currentUser.name);
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
        StorageReference reference = FirebaseStorage.getInstance().getReference();

        public UserViewHolder(View itemView) {
            super(itemView);
            rview = itemView;
        }

        public void setImage(String uid) throws IOException {
//            ImageView avatar = rview.findViewById(R.id.avatar);
//            reference.child("myImages/" + uid).getDownloadUrl().addOnSuccessListener(uri -> {
//                Picasso.get(rview).load(uri).into((Target) avatar.getBackground());
//            });
        }

        public void setName(String name) {
            TextView userName = rview.findViewById(R.id.nameOfUser);
            userName.setText(name);
        }

    }


}
