package com.cos.javagg.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.fragment.DetailPostFragment;
import com.cos.javagg.model.board.Board;
import com.cos.javagg.service.CommunityApi;

import java.util.List;

import info.androidhive.fontawesome.FontTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.MyViewHolder> {
    private final List<Board> posts;
    private MainActivity at;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private CommunityApi communityApi;
    private Call<CMRespDto<Board>> call;

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
        private TextView tv_title, tv_postkinds, tv_posthoursago, tv_postusername, tv_postlikecount, tv_reply_ccccount;
        private ImageView iv_postimage;
        private Board board;
        private FontTextView ftv_likebtn;
        private boolean togle = false;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            findById(itemView);

            listener(itemView);


        }

        private void listener(View itemView) {
            itemView.setOnClickListener(view -> {
                Toast.makeText(view.getContext(), "이동됨", Toast.LENGTH_SHORT).show();

                MainActivity.board = board;

                //여기서 조회수 해야함
                communityApi = CommunityApi.retrofit.create(CommunityApi.class);
                call = communityApi.count(board.getId());
                call.enqueue(new Callback<CMRespDto<Board>>() {
                    @Override
                    public void onResponse(Call<CMRespDto<Board>> call, Response<CMRespDto<Board>> response) {
                        CMRespDto<Board> cmRespDto = response.body();
                        //MainActivity.board = cmRespDto.getData();

                        at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DetailPostFragment()).commit();
                    }

                    @Override
                    public void onFailure(Call<CMRespDto<Board>> call, Throwable t) {

                    }
                });

            });

            //좋아요 클릭하면
            ftv_likebtn.setOnClickListener(view -> {
                if (togle == false){
                    ftv_likebtn.setTextColor(Color.GREEN);
                    togle = true;
                }else{
                    ftv_likebtn.setTextColor(Color.rgb(170,170,170));
                    togle = false;
                }

            });
        }

        private void findById(View itemView) {
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_postkinds = itemView.findViewById(R.id.tv_postkinds);
            tv_posthoursago = itemView.findViewById(R.id.tv_posthoursago);
            tv_postusername = itemView.findViewById(R.id.tv_postusername);
            iv_postimage = itemView.findViewById(R.id.iv_postimage);
            tv_postlikecount = itemView.findViewById(R.id.tv_reply_likecount);

            ftv_likebtn = itemView.findViewById(R.id.ftv_likebtn);

            tv_reply_ccccount = itemView.findViewById(R.id.tv_reply_ccccount);
        }

        public void setItem(Board post) {
            this.board = post;
            tv_title.setText(post.getTitle());
            tv_postkinds.setText(post.getCommunityType());
            tv_posthoursago.setText(Calcu.getDate(post.getCreateDate()));
            tv_postusername.setText(post.getUser().getUsername());
            tv_postlikecount.setText(post.getLikeCount()+"");

            if(post.getReplys() == null){
                tv_reply_ccccount.setText("");
            }else{
                tv_reply_ccccount.setText("["+post.getReplys().size()+"]");
            }
        }
    }
}
