package com.cos.javagg.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.javagg.MatchDetailActivity;
import com.cos.javagg.R;
import com.cos.javagg.SearchResultActivity;

import java.util.List;

public class MatchListAdapter extends RecyclerView.Adapter<MatchListAdapter.MyViewHolder> {
    private final List<Integer> matchs;
    private SearchResultActivity sr;

    public MatchListAdapter(List<Integer> matchs) {
        this.matchs = matchs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sr = (SearchResultActivity)parent.getContext();
        LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.match_item, parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return matchs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                // 리사이클러뷰에서 액티비티 전환하기
                Intent intent = new Intent(sr, MatchDetailActivity.class);
//                intent.putExtra("gameId", ApiSummoner.getId());
//                intent.putExtra("nowSummoner", nowSummoner);

                sr.startActivity(intent);

            });

        }
    }
}
