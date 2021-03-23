package com.cos.javagg.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cos.javagg.MatchDetailActivity;
import com.cos.javagg.R;
import com.cos.javagg.champ.ChampionList;
import com.cos.javagg.champ.Calcu;
import com.cos.javagg.model.detail.Participant;
import com.cos.javagg.model.detail.ParticipantIdentity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchDetailWinListAdapter extends RecyclerView.Adapter<MatchDetailWinListAdapter.MyViewHolder> {
    private static final String TAG = "MatchDetailWinListAdapt";
    private final List<Participant> participants;
    private final List<ParticipantIdentity> participantIdentities;
    private final long duration;
    private String spell1, spell2, item1,item2,item3,item4,item5,item6, item0 = "";
    private MatchDetailActivity md;
    private ChampionList championList = new ChampionList();
    private Calcu cal= new Calcu();

    public MatchDetailWinListAdapter(List<Participant> participants, List<ParticipantIdentity> participantIdentities, long duration) {
        this.participants = participants;
        this.participantIdentities = participantIdentities;
        this.duration = duration;
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
        holder.setItem(participants.get(position));
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView ivDetailChampion;
        private ImageView iv_detail_spell1, iv_detail_spell2, iv_detail_perk1, iv_detail_perk2, iv_detail_item0, iv_detail_item1,
                iv_detail_item2, iv_detail_item3, iv_detail_item4 ,iv_detail_item5, iv_detail_item6;
        private TextView iv_detail_summoner, iv_detail_kill, iv_detail_death, iv_detail_assist, iv_detail_grade,
                iv_detail_cs, tv_assist_solo, iv_detail_gold;
        private Button iv_detail_champ_level;
        private ProgressBar pg_detail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            findById(itemView);

        }

        public void setItem(Participant participant){
            //챔피온, 스펠, 특성
            String champ = championList.getChampName(participant.getChampionId());
            loadChampImages(champ, ivDetailChampion);
            spell1 = participant.getSpell1Id()+"";
            spell2 = participant.getSpell2Id()+"";



            loadSpellImages(championList.getSpell(spell1), championList.getSpell(spell2),iv_detail_spell1 , iv_detail_spell2);

            //이름
            for (int i = 0; i< participantIdentities.size(); i++){
                if(participantIdentities.get(i).getParticipantId() == participant.getParticipantId()){
                    iv_detail_summoner.setText(participantIdentities.get(i).getPlayer().getSummonerName());
                }
            }

            //킬뎃어시
            iv_detail_kill.setText(participant.getStats().getKills()+"");
            iv_detail_death.setText(participant.getStats().getDeaths()+"");
            iv_detail_assist.setText(participant.getStats().getAssists()+"");
            //평점
            iv_detail_grade.setText(cal.getGrade(participant.getStats().getKills(),participant.getStats().getDeaths(),participant.getStats().getAssists()));

            //아이템
            itemImages(participant.getStats().getItem0()+"", iv_detail_item0);
            itemImages(participant.getStats().getItem1()+"", iv_detail_item1);
            itemImages(participant.getStats().getItem2()+"", iv_detail_item2);
            itemImages(participant.getStats().getItem3()+"", iv_detail_item3);
            itemImages(participant.getStats().getItem4()+"", iv_detail_item4);
            itemImages(participant.getStats().getItem5()+"", iv_detail_item5);
            itemImages(participant.getStats().getItem6()+"", iv_detail_item6);

            //시간당cs
            iv_detail_cs.setText(Calcu.getCS(participant.getStats().getTotalMinionsKilled() , duration));

            //골드
            iv_detail_gold.setText(Calcu.getGold(participant.getStats().getGoldEarned()));

            //레벨
            iv_detail_champ_level.setText(participant.getStats().getChampLevel()+"");

        }

        public void findById(View itemView){
            ivDetailChampion = itemView.findViewById(R.id.iv_detail_profile);
            iv_detail_champ_level = itemView.findViewById(R.id.iv_detail_champ_level);
            iv_detail_spell1 = itemView.findViewById(R.id.iv_detail_spell1);
            iv_detail_spell2 = itemView.findViewById(R.id.iv_detail_spell2);
            iv_detail_perk1 = itemView.findViewById(R.id.iv_detail_perk1);
            iv_detail_perk2 = itemView.findViewById(R.id.iv_detail_perk2);
            iv_detail_item0 = itemView.findViewById(R.id.iv_detail_item0);
            iv_detail_item1 = itemView.findViewById(R.id.iv_detail_item1);
            iv_detail_item2 = itemView.findViewById(R.id.iv_detail_item2);
            iv_detail_item3 = itemView.findViewById(R.id.iv_detail_item3);
            iv_detail_item4 = itemView.findViewById(R.id.iv_detail_item4);
            iv_detail_item5 = itemView.findViewById(R.id.iv_detail_item5);
            iv_detail_item6 = itemView.findViewById(R.id.iv_detail_item6);

            iv_detail_summoner = itemView.findViewById(R.id.iv_detail_summoner);
            iv_detail_kill = itemView.findViewById(R.id.iv_detail_kill);
            iv_detail_death = itemView.findViewById(R.id.iv_detail_death);
            iv_detail_assist = itemView.findViewById(R.id.iv_detail_assist);
            iv_detail_grade = itemView.findViewById(R.id.iv_detail_grade);
            iv_detail_cs = itemView.findViewById(R.id.iv_detail_cs);
            tv_assist_solo = itemView.findViewById(R.id.tv_assist_solo);
            iv_detail_gold = itemView.findViewById(R.id.iv_detail_gold);
            pg_detail = itemView.findViewById(R.id.pg_detail);
        }

        //https://ddragon.leagueoflegends.com/cdn/img/perk-images/Styles/Domination/Electrocute/Electrocute.png

        public void loadChampImages(String id, CircleImageView civChampionimg) {
            // 이미지뷰 가져오기
            Glide
                    .with(md)
                    .load("https://ddragon.leagueoflegends.com/cdn/11.6.1/img/champion/"+id+".png")
                    .centerCrop()
                    .into(civChampionimg);
        }
        public void loadSpellImages(String spell1, String spell2, ImageView imageView1, ImageView imageView2) {
            // 이미지뷰 가져오기
            Glide
                    .with(md)
                    .load("https://ddragon.leagueoflegends.com/cdn/10.6.1/img/spell/"+spell1+".png")
                    .centerCrop()
                    .into(imageView1);

            Glide
                    .with(md)
                    .load("https://ddragon.leagueoflegends.com/cdn/10.6.1/img/spell/"+spell2+".png")
                    .centerCrop()
                    .into(imageView2);
        }

        public void itemImages(String itemImage, ImageView imageView) {
            if(itemImage.equals("0")){
                imageView.setBackgroundColor(Color.parseColor("#C5CBD0"));
                Log.d(TAG, "itemImages: null값 있음");
            }else{
                // 이미지뷰 가져오기
                Glide
                        .with(md)
                        .load("https://ddragon.leagueoflegends.com/cdn/11.6.1/img/item/"+itemImage+".png")
                        .centerCrop()
                        .into(imageView);
            }

        }
    }
}
