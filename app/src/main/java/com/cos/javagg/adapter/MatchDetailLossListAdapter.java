package com.cos.javagg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.javagg.MatchDetailActivity;
import com.cos.javagg.R;
import com.cos.javagg.SearchResultActivity;

import java.util.List;

public class MatchDetailLossListAdapter extends RecyclerView.Adapter<MatchDetailLossListAdapter.MyViewHolder> {
    private final List<Integer> matchsSummoner;
    private MatchDetailActivity md;

    public MatchDetailLossListAdapter(List<Integer> matchsSummoner) {
        this.matchsSummoner = matchsSummoner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        md = (MatchDetailActivity)parent.getContext();
        LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.match_detail_items, parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return matchsSummoner.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
