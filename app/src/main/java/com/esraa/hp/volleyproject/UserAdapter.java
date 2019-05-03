package com.esraa.hp.volleyproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    ArrayList<UserDetails> details;
    UserDetails userDetails;
    Context context;

    public UserAdapter (Context context,ArrayList<UserDetails> details){
        this.details=details;
        this.context=context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_row,viewGroup,false);
        MyViewHolder holder=new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        userDetails=details.get(i);
        myViewHolder.name.setText(userDetails.getName());
        myViewHolder.likes.append(" "+userDetails.getLikes());
        Picasso.with(context).load(userDetails.getImageUrl()).into(myViewHolder.profileImage);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView likes;
        ImageView profileImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.txt1);
            likes=itemView.findViewById(R.id.txt2);
            profileImage=itemView.findViewById(R.id.img);
        }
    }
}

