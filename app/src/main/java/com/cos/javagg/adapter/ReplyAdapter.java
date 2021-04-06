package com.cos.javagg.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.javagg.MainActivity;
import com.cos.javagg.R;
import com.cos.javagg.champ.Calcu;
import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.fragment.CommunityFragment;
import com.cos.javagg.fragment.DetailPostFragment;
import com.cos.javagg.model.board.Board;
import com.cos.javagg.model.reply.Reply;
import com.cos.javagg.service.CommunityApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void addItem(Reply reply){
        replies.add(reply);
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        replies.remove(position);
        notifyItemRemoved(position);

        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.setItem(replies.get(position), position);
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_reply_username, tv_reply_createdate, tv_reply_content, tv_reply_likecount, tv_reply_delete;
        private CommunityApi communityApi;
        private Call<CMRespDto<String>> call;

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
            tv_reply_delete=view.findViewById(R.id.tv_reply_delete);
        }

        public void setItem(Reply reply, int position) {
            tv_reply_username.setText(reply.getUser().getUsername());
            tv_reply_createdate.setText(Calcu.getDate(reply.getCreateDate()));
            tv_reply_content.setText(reply.getContent());
            tv_reply_likecount.setText(reply.getLikeCount()+"");

            if(MainActivity.loginUser != null){
                if(reply.getUser().getId().equals(MainActivity.loginUser.getId())){
                    tv_reply_delete.setVisibility(View.VISIBLE);

                    tv_reply_delete.setOnClickListener(v -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(at);

                        builder.setTitle("").setMessage("삭제 하시겠습니까?");

                        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                                //삭제 로직
                                communityApi = CommunityApi.retrofit.create(CommunityApi.class);
                                call = communityApi.replyDelete(reply.getId());

                                call.enqueue(new Callback<CMRespDto<String>>() {
                                    @Override
                                    public void onResponse(Call<CMRespDto<String>> call, Response<CMRespDto<String>> response) {
                                        Toast.makeText(at, "삭제되었습니다", Toast.LENGTH_SHORT).show();
                                        removeItem(position);
                                    }

                                    @Override
                                    public void onFailure(Call<CMRespDto<String>> call, Throwable t) {

                                    }
                                });
                            }
                        });

                        builder.setNegativeButton("닫기",null);

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    });
                }
            }


        }
    }
}
