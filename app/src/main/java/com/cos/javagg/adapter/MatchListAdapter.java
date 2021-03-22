package com.cos.javagg.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cos.javagg.MatchDetailActivity;
import com.cos.javagg.R;
import com.cos.javagg.SearchResultActivity;
import com.cos.javagg.champ.ChampionList;
import com.cos.javagg.model.api.ApiMatch;
import com.cos.javagg.model.api.ApiMatchEntry;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchListAdapter extends RecyclerView.Adapter<MatchListAdapter.MyViewHolder> {
    private static final String TAG = "MatchListAdapter";
    private final List<ApiMatch> apiMatches;
    private final ApiMatchEntry apiMatchEntry;
    private SearchResultActivity sr;
    private ChampionList championList = new ChampionList();

    public MatchListAdapter(ApiMatchEntry apiMatchEntry, List<ApiMatch> apiMatches) {
        this.apiMatchEntry = apiMatchEntry;
        this.apiMatches = apiMatches;
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
        holder.setItem(apiMatches.get(position));

    }

    @Override
    public int getItemCount() {
        return apiMatches.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvWinOrLoss;
        private String champKey = "";
        private CircleImageView civChampionimg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            civChampionimg = (CircleImageView) itemView.findViewById(R.id.civ_championimg);
            tvWinOrLoss = itemView.findViewById(R.id.tv_win_or_loss);





            itemView.setOnClickListener(v -> {
                // 리사이클러뷰에서 액티비티 전환하기
                Intent intent = new Intent(sr, MatchDetailActivity.class);
//                intent.putExtra("gameId", ApiSummoner.getId());
//                intent.putExtra("nowSummoner", nowSummoner);

                sr.startActivity(intent);

            });

        }

        public void setItem(ApiMatch apiMatch){
            for(int i = 0; i< apiMatches.size(); i++){
                if(apiMatchEntry.getMatches().get(i).getGameId() == apiMatch.getGameId()){
                    champKey = championList.getChampName(apiMatchEntry.getMatches().get(i).getChampion());
//                    Log.d(TAG, "MyViewHolder: 챔피온 아이디는 ?  : " + champKey);

                }
            }

            loadImages(champKey, civChampionimg);



        }

        public void loadImages(String id, CircleImageView civChampionimg) {
            // 이미지뷰 가져오기
            Glide
                    .with(sr)
                    .load("https://ddragon.leagueoflegends.com/cdn/10.6.1/img/champion/"+id+".png")
                    .centerCrop()
                    .into(civChampionimg);
        }

    }
}
