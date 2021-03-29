package com.cos.javagg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.javagg.MainActivity;
import com.cos.javagg.R;
import com.cos.javagg.champ.Calcu;
import com.cos.javagg.fragment.DetailPostFragment;
import com.cos.javagg.model.board.Board;

import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.MyViewHolder> {
    private final List<Board> posts;
    private MainActivity at;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public CommunityAdapter(List<Board> posts) {
        this.posts = posts;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        at = (MainActivity)parent.getContext();
        LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.communitypost_item, parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setItem(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title, tv_postkinds, tv_posthoursago, tv_postusername;
        private ImageView iv_postimage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            findById(itemView);


            itemView.setOnClickListener(view -> {
                Toast.makeText(view.getContext(), "이동됨", Toast.LENGTH_SHORT).show();
                at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DetailPostFragment()).commit();
            });
        }

        private void findById(View itemView) {
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_postkinds = itemView.findViewById(R.id.tv_postkinds);
            tv_posthoursago = itemView.findViewById(R.id.tv_posthoursago);
            tv_postusername = itemView.findViewById(R.id.tv_postusername);
            iv_postimage = itemView.findViewById(R.id.iv_postimage);
        }

        public void setItem(Board post) {
            tv_title.setText(post.getTitle());
            tv_postkinds.setText("아직일반");
            tv_posthoursago.setText(Calcu.getDate(post.getCreateDate()));
            tv_postusername.setText(post.getUser().getUsername());
        }
    }
}
