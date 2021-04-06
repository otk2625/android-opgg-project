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
import com.cos.javagg.MainActivity;
import com.cos.javagg.R;
import com.cos.javagg.SearchResultActivity;
import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.RankingDto;
import com.cos.javagg.model.api.ApiMatch;
import com.cos.javagg.model.api.ApiSummoner;
import com.cos.javagg.model.detail.Entry;
import com.cos.javagg.service.SummonerApi;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankedAdapter extends RecyclerView.Adapter<RankedAdapter.ViewHolder> {

    private  List<RankingDto> rankingDtos;
    private static int rank = 1;
    private MainActivity at;

    public RankedAdapter( List<RankingDto> rankingDtos) {
        this.rankingDtos = rankingDtos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        at = (MainActivity)parent.getContext();
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.ranked_item, parent, false) ;
        ViewHolder vh = new ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setItem(rankingDtos.get(position), rank);
        rank++;
    }

    @Override
    public int getItemCount() {
        return rankingDtos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mrank;
        private TextView mname;
        private TextView mtier;
        private TextView mlp;
        private Call<CMRespDto<ApiSummoner>> call;
        private SummonerApi summonerApi;
        private CircleImageView iv_rankImage;
        private String sName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mrank = itemView.findViewById(R.id.tv_rank);
            mname = itemView.findViewById(R.id.tv_ranked_name);
            mtier = itemView.findViewById(R.id.tv_tier);
            mlp = itemView.findViewById(R.id.tv_lp);
            iv_rankImage = itemView.findViewById(R.id.iv_rankImage);
            summonerApi = SummonerApi.retrofit.create(SummonerApi.class);

            itemView.setOnClickListener(view -> {
                String 공백제거 = sName.replace(" ", "");
                Intent intent = new Intent(at, SearchResultActivity.class);
                intent.putExtra("summonerName", 공백제거);
                at.startActivity(intent);
            });

        }

        public void setItem(RankingDto rankingDto, int rank) {
            sName = rankingDto.getSummonerName();
            mrank.setText(rankingDto.getRank()+"");
            mname.setText(rankingDto.getSummonerName());
            mtier.setText("CHALLENGER");
            mlp.setText(rankingDto.getLeaguePoints()+"");


               rankingDto.getSummonerName().replaceAll(" ","");
               call = summonerApi.getRankSummoner(rankingDto.getSummonerId());

               call.enqueue(new Callback<CMRespDto<ApiSummoner>>() {
                   @Override
                   public void onResponse(Call<CMRespDto<ApiSummoner>> call, Response<CMRespDto<ApiSummoner>> response) {
                       CMRespDto<ApiSummoner> cmRespDto = response.body();

                       if(cmRespDto.getData().getProfileIconId()+"" != null){
                           Glide
                                   .with(at)
                                   .load("http://ddragon.leagueoflegends.com/cdn/11.7.1/img/profileicon/"+cmRespDto.getData().getProfileIconId()+".png")
                                   .centerCrop()
                                   .into(iv_rankImage);
                       }

                   }

                   @Override
                   public void onFailure(Call<CMRespDto<ApiSummoner>> call, Throwable t) {

                   }
               });
           }


    }
}
