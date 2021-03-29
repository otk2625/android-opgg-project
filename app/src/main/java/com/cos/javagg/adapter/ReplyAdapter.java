package com.cos.javagg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.javagg.MainActivity;
import com.cos.javagg.R;
import com.cos.javagg.fragment.DetailPostFragment;

import java.util.List;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.MyViewHolder> {
    private final List<Integer> posts;
    private MainActivity at;

    public ReplyAdapter(List<Integer> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        at = (MainActivity)parent.getContext();
        LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.communityreply_item, parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
