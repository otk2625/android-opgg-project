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
import com.cos.javagg.champ.ChampionList;
import com.cos.javagg.model.detail.Participant;
import com.cos.javagg.model.detail.ParticipantIdentity;

import java.util.List;

public class MatchDetailLossListAdapter extends RecyclerView.Adapter<MatchDetailLossListAdapter.MyViewHolder> {
    private final List<Participant> participants;
    private final List<ParticipantIdentity> participantIdentities;
    private MatchDetailActivity md;
    private ChampionList championList = new ChampionList();

    public MatchDetailLossListAdapter(List<Participant> participants, List<ParticipantIdentity> participantIdentities, long duration) {
        this.participants = participants;
        this.participantIdentities = participantIdentities;
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
        return participants.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
