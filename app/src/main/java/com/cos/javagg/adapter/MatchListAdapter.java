package com.cos.javagg.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cos.javagg.MatchDetailActivity;
import com.cos.javagg.R;
import com.cos.javagg.SearchResultActivity;
import com.cos.javagg.champ.Calcu;
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
    private String queueType; //420솔랭, 430일반, 440무작위, 1020 단일 챔피언



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

        private TextView tvWinOrLoss, tvKillSolo, tvDeathSolo, tvAssistSolo, tvGameType, tv_timestamp, tv_game_cration;
        private String champKey, spell1, spell2, item1,item2,item3,item4,item5,item6, item0,ChampionId = "";
        private CircleImageView civChampionimg;
        private ImageView ivSpell1, ivSpell2, tvItem0,tvItem1,tvItem2,tvItem3,tvItem4,tvItem5,tvItem6;
        private ConstraintLayout clWinOrLoss;
        private String matchGameId = "";


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            findById();
            ApiMatch apiMatch2 = new ApiMatch();

            itemView.setOnClickListener(v -> {
                // 리사이클러뷰에서 액티비티 전환하기
                Intent intent = new Intent(sr, MatchDetailActivity.class);
                intent.putExtra("matchGameId", matchGameId);
                intent.putExtra("UserChampionId", ChampionId);

                sr.startActivity(intent);


            });

        }
        public void findById(){
            civChampionimg = (CircleImageView) itemView.findViewById(R.id.civ_championimg);
            tvWinOrLoss = itemView.findViewById(R.id.tv_win_or_loss);
            ivSpell1 = itemView.findViewById(R.id.iv_spell1);
            ivSpell2 = itemView.findViewById(R.id.iv_spell2);
            clWinOrLoss = itemView.findViewById(R.id.cl_win_or_loss);
            tvKillSolo = itemView.findViewById(R.id.tv_kill_solo);
            tvDeathSolo = itemView.findViewById(R.id.tv_teamdragon);
            tvAssistSolo = itemView.findViewById(R.id.tv_assist_solo);
            tvItem0 = itemView.findViewById(R.id.iv_item0);
            tvItem1 = itemView.findViewById(R.id.iv_item1);
            tvItem2 = itemView.findViewById(R.id.iv_item2);
            tvItem3 = itemView.findViewById(R.id.iv_item3);
            tvItem4 = itemView.findViewById(R.id.iv_item4);
            tvItem5 = itemView.findViewById(R.id.iv_item5);
            tvItem6 = itemView.findViewById(R.id.iv_item6);
            tvGameType = itemView.findViewById(R.id.tv_game_type);
            tv_timestamp = itemView.findViewById(R.id.tv_timestamp);
            tv_game_cration = itemView.findViewById(R.id.tv_game_cration);
        }

        public void setItem(ApiMatch apiMatch){
            //시간 duration
            tv_timestamp.setText(Calcu.getDuration(apiMatch.getGameDuration()));
            tv_game_cration.setText(Calcu.getCreation2(apiMatch.getGameCreation()));



            //매치 리스트에 보여줄 챔피온 id값 구하기
            for(int i = 0; i< apiMatches.size(); i++){
                if(apiMatchEntry.getMatches().get(i).getGameId() == apiMatch.getGameId()){
                    champKey = championList.getChampName(apiMatchEntry.getMatches().get(i).getChampion());
                    ChampionId = apiMatchEntry.getMatches().get(i).getChampion()+"";
                    matchGameId = apiMatchEntry.getMatches().get(i).getGameId()+"";
                    break;
                }
            }

            //각 매치에 해당하는 소환사의 게임 정보
            for(int i = 0; i< apiMatches.size(); i++){
                if(apiMatchEntry.getMatches().get(i).getGameId() == apiMatch.getGameId()){
                    for (int j = 0; j < apiMatch.getParticipants().size(); j++){
                        if(apiMatchEntry.getMatches().get(i).getChampion() == apiMatch.getParticipants().get(j).getChampionId()){
                            spell1 = championList.getSpell(apiMatch.getParticipants().get(j).getSpell1Id()+"");
                            spell2 = championList.getSpell(apiMatch.getParticipants().get(j).getSpell2Id()+"");

                            if(apiMatch.getParticipants().get(j).getStats().isWin() == false){
                                tvWinOrLoss.setText("패");
                                clWinOrLoss.setBackgroundColor(Color.parseColor("#E91E63"));
                            }else {
                                tvWinOrLoss.setText("승");
                                clWinOrLoss.setBackgroundColor(Color.parseColor("#5383E9"));
                            }

                            queueType = apiMatch.getQueueId()+"";
                            if(queueType.equals("420")){
                                tvGameType.setText("솔랭");
                            }else if(queueType.equals("430")){
                                tvGameType.setText("일반");
                            }else if(queueType.equals("1020")){
                                tvGameType.setText("단일 챔피언");
                            }else {
                                tvGameType.setText("칼바람");
                            }


                            //킬 뎃 어시
                            tvKillSolo.setText(apiMatch.getParticipants().get(j).getStats().getKills()+"");
                            tvDeathSolo.setText(apiMatch.getParticipants().get(j).getStats().getDeaths()+"");
                            tvAssistSolo.setText(apiMatch.getParticipants().get(j).getStats().getAssists()+"");

                            // 템
                            item0 = apiMatch.getParticipants().get(j).getStats().getItem0()+"";
                            item1 = apiMatch.getParticipants().get(j).getStats().getItem1()+"";
                            item2 = apiMatch.getParticipants().get(j).getStats().getItem2()+"";
                            item3 = apiMatch.getParticipants().get(j).getStats().getItem3()+"";
                            item4 = apiMatch.getParticipants().get(j).getStats().getItem4()+"";
                            item5 = apiMatch.getParticipants().get(j).getStats().getItem5()+"";
                            item6 = apiMatch.getParticipants().get(j).getStats().getItem6()+"";

                            break;
                        }
                    }

                }
            }


            itemImages(item0,tvItem0); itemImages(item1,tvItem1); itemImages(item2,tvItem2); itemImages(item3,tvItem3);
            itemImages(item4,tvItem4); itemImages(item5,tvItem5); itemImages(item6,tvItem6);
            loadChampImages(champKey, civChampionimg);
            loadSpellImages(spell1,spell2,ivSpell1,ivSpell2);


        }

        public void loadChampImages(String id, CircleImageView civChampionimg) {
            // 이미지뷰 가져오기
            Glide
                    .with(sr)
                    .load("https://ddragon.leagueoflegends.com/cdn/11.6.1/img/champion/"+id+".png")
                    .centerCrop()
                    .into(civChampionimg);
        }
        public void loadSpellImages(String spell1, String spell2, ImageView imageView1, ImageView imageView2) {
            // 이미지뷰 가져오기
            Glide
                    .with(sr)
                    .load("https://ddragon.leagueoflegends.com/cdn/10.6.1/img/spell/"+spell1+".png")
                    .centerCrop()
                    .into(imageView1);

            Glide
                    .with(sr)
                    .load("https://ddragon.leagueoflegends.com/cdn/10.6.1/img/spell/"+spell2+".png")
                    .centerCrop()
                    .into(imageView2);
        }

        public void itemImages(String itemImage, ImageView imageView) {
            if(itemImage.isEmpty() == true){
                Log.d(TAG, "itemImages: null값 있음");
            }else{
                // 이미지뷰 가져오기
                Glide
                        .with(sr)
                        .load("https://ddragon.leagueoflegends.com/cdn/11.6.1/img/item/"+itemImage+".png")
                        .centerCrop()
                        .into(imageView);
            }

        }



    }
}
