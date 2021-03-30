package com.cos.javagg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.javagg.MainActivity;
import com.cos.javagg.R;
import com.cos.javagg.champ.Calcu;
import com.cos.javagg.fragment.DetailPostFragment;
import com.cos.javagg.model.reply.Reply;

import java.util.List;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.MyViewHolder> {
    private final List<Reply> replies;
    private MainActivity at;

    public ReplyAdapter(List<Reply> replies) {
        this.replies = replies;
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

        holder.setItem(replies.get(position));
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_reply_username, tv_reply_createdate, tv_reply_content, tv_reply_likecount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            findById(itemView);

        }

        private void findById(View view) {
            //댓글
            tv_reply_username = view.findViewById(R.id.tv_reply_username);
            tv_reply_createdate = view.findViewById(R.id.tv_reply_createdate);
            tv_reply_content = view.findViewById(R.id.tv_reply_content);
            tv_reply_likecount = view.findViewById(R.id.tv_reply_likecount);
        }

        public void setItem(Reply reply) {
            tv_reply_username.setText(reply.getUser().getUsername());
            tv_reply_createdate.setText(Calcu.getDate(reply.getCreateDate()));
            tv_reply_content.setText(reply.getContent());
            tv_reply_likecount.setText(reply.getLikeCount()+"");
        }
    }
}
