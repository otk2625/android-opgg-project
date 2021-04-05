package com.cos.javagg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cos.javagg.adapter.MatchDetailLossListAdapter;
import com.cos.javagg.adapter.MatchDetailWinListAdapter;
import com.cos.javagg.champ.Calcu;
import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.model.api.ApiMatch;
import com.cos.javagg.model.detail.Participant;
import com.cos.javagg.model.detail.ParticipantIdentity;
import com.cos.javagg.service.SummonerApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchDetailActivity extends AppCompatActivity {
    private static final String TAG = "MatchDetailActivity";
    private RecyclerView rvDetailWin, rvDetailLoss;
    private LinearLayoutManager manger, manger2;
    private MatchDetailWinListAdapter matchDetailWinListAdapter;
    private MatchDetailLossListAdapter matchDetailLossListAdapter;
    private ImageView ivInfoDetailBack;
    private String matchGameId, userChampionId = "";
    private Call<CMRespDto<ApiMatch>> call;
    private SummonerApi summonerApi;

    private RelativeLayout layoutDetailHeader1;
    private ConstraintLayout layoutDetailHeader2;
    private TextView tvDetailHeaderWinOrLose, tvDetailWinTeamKill, tvDetailWinTeamDeath, tvDetailWinTeamAssist, tvTeamBaron,
            tvTeamDragon, tvTeamPotop, tvDetailLoseTeamKill, tvDetailLoseTeamDeath, tvDetailLoseTeamAssist, tvTeamLossBaron,
            tvTeamLoseDragon, tvTeamLosePotop, tv_detail_header_queuetype, tv_detail_header_duration, tv_detail_header_createdate;
    private List<Participant> winTeamList;
    private List<Participant> loseTeamList;
    private ParticipantIdentity participantIdentity;
    private String queueType; //420솔랭, 430일반, 440무작위, 1020 단일
    private Calcu cal = new Calcu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);


        findId();
        listener();


        Intent intent = getIntent();
        matchGameId = (String) intent.getSerializableExtra("matchGameId");
        //유저 챔피온 id
        userChampionId = (String) intent.getSerializableExtra("UserChampionId");
        Log.d(TAG, "onCreate: apimatch정보 제발 가져아져라 : " + matchGameId);

        call = summonerApi.getMatchGameId(matchGameId);
        matchInfo();
    }

    private void findId() {
        rvDetailWin = findViewById(R.id.rv_detail_win);
        rvDetailLoss = findViewById(R.id.rv_detail_lose);
        ivInfoDetailBack = findViewById(R.id.iv_info_detail_back);
        summonerApi = SummonerApi.retrofit.create(SummonerApi.class);
        tvDetailHeaderWinOrLose = findViewById(R.id.tv_detail_header_winorlose);
        layoutDetailHeader2 = findViewById(R.id.layout_detail_header2);
        layoutDetailHeader1 = findViewById(R.id.layout_detail_header1);
        tv_detail_header_queuetype = findViewById(R.id.tv_detail_header_queuetype);
        tv_detail_header_duration = findViewById(R.id.tv_detail_header_duration);
        tv_detail_header_createdate = findViewById(R.id.tv_detail_header_createdate);
        //팀 게임 정보
        //승리팀
        tvDetailWinTeamKill = findViewById(R.id.tv_detail_win_team_kill);
        tvDetailWinTeamDeath = findViewById(R.id.tv_detail_win_team_death);
        tvDetailWinTeamAssist = findViewById(R.id.tv_detail_win_team_assist);
        tvTeamBaron = findViewById(R.id.tv_teambaron);
        tvTeamDragon = findViewById(R.id.tv_teamdragon);
        tvTeamPotop = findViewById(R.id.tv_teampotop);
        //패배팀
        tvDetailLoseTeamKill = findViewById(R.id.tv_detail_lose_team_kill);
        tvDetailLoseTeamDeath = findViewById(R.id.tv_detail_lose_team_death);
        tvDetailLoseTeamAssist = findViewById(R.id.tv_detail_lose_team_assist);
        tvTeamLossBaron = findViewById(R.id.tv_team_lose_Baron);
        tvTeamLoseDragon = findViewById(R.id.tv_team_lose_dragon);
        tvTeamLosePotop = findViewById(R.id.tv_team_lose_tower);
    }

    public void adapter(List<Participant> winTeamList, List<Participant> loseTeamList, List<ParticipantIdentity> participantIdentities, long duration){
        manger = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        manger2 = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rvDetailWin.setLayoutManager(manger);
        rvDetailLoss.setLayoutManager(manger2);
        matchDetailWinListAdapter = new MatchDetailWinListAdapter(winTeamList, participantIdentities, duration);
        matchDetailLossListAdapter = new MatchDetailLossListAdapter(loseTeamList, participantIdentities, duration);
        rvDetailWin.setAdapter(matchDetailWinListAdapter);
        rvDetailLoss.setAdapter(matchDetailLossListAdapter);
    }
    public void listener(){
        ivInfoDetailBack.setOnClickListener(v -> {
            Log.d(TAG, "listener: 실행됨");
            onBackPressed();
        });
    }

    public void matchInfo(){
        call.enqueue(new Callback<CMRespDto<ApiMatch>>() {
            @Override
            public void onResponse(Call<CMRespDto<ApiMatch>> call, Response<CMRespDto<ApiMatch>> response) {
                CMRespDto<ApiMatch> cmRespDto = response.body();
                //게임 정보
                ApiMatch apiMatch = cmRespDto.getData();
                boolean isWin = true;
                String teamId = "";


                queueType = apiMatch.getQueueId()+"";
                if(queueType.equals("420")){
                    tv_detail_header_queuetype.setText("솔랭");
                }else if(queueType.equals("430")){
                    tv_detail_header_queuetype.setText("일반");
                }else if(queueType.equals("1020")){
                    tv_detail_header_queuetype.setText("단일 챔피언");
                }else {
                    tv_detail_header_queuetype.setText("칼바람");
                }


                long duration = apiMatch.getGameDuration();
                tv_detail_header_duration.setText(Calcu.getDuration(duration));

                long gameCreation = apiMatch.getGameCreation();
                tv_detail_header_createdate.setText(Calcu.getCreation(gameCreation));

                for(int i = 0; i<apiMatch.getParticipants().size(); i++){
                    String temp = apiMatch.getParticipants().get(i).getChampionId()+"";
                    if(temp.equals(userChampionId)){
                        isWin = apiMatch.getParticipants().get(i).getStats().isWin();
                    }
                }

                //승리 또는 패배시
                if(isWin == false){
                    tvDetailHeaderWinOrLose.setText("패배");
                    layoutDetailHeader2.setBackgroundColor(Color.parseColor("#E91E63"));
                    layoutDetailHeader1.setBackgroundColor(Color.parseColor("#E91E63"));
                }else {

                }
//        tvDetailWinTeamKill, tvDetailWinTeamDeath, tvDetailWinTeamAssist,

                for(int i =0; i<apiMatch.getTeams().size(); i++){
                    //이긴팀
                    if(apiMatch.getTeams().get(i).getWin().equals("Win")){
                        teamId = apiMatch.getTeams().get(i).getTeamId()+"";
                        tvTeamBaron.setText(apiMatch.getTeams().get(i).getBaronKills()+"");
                        tvTeamDragon.setText(apiMatch.getTeams().get(i).getDragonKills()+"");
                        tvTeamPotop.setText(apiMatch.getTeams().get(i).getTowerKills()+"");
                    }else { //진팀
                        tvTeamLossBaron.setText(apiMatch.getTeams().get(i).getBaronKills()+"");
                        tvTeamLoseDragon.setText(apiMatch.getTeams().get(i).getDragonKills()+"");
                        tvTeamLosePotop.setText(apiMatch.getTeams().get(i).getTowerKills()+"");
                    }
                }

                winTeamList = new ArrayList<>();
                loseTeamList = new ArrayList<>();
                for(int i = 0; i<apiMatch.getParticipants().size(); i++){
                    String temp = apiMatch.getParticipants().get(i).getTeamId()+"";
                    //이긴팀 리스트 담기
                    if(temp.equals(teamId)){
                        winTeamList.add(apiMatch.getParticipants().get(i));
                    } else {//진팀
                        loseTeamList.add(apiMatch.getParticipants().get(i));
                    }
                }

                List<ParticipantIdentity> participantIdentities = apiMatch.getParticipantIdentities();

                long teamKills= 0L , teamDeaths= 0L, teamAssists = 0L;

                //이긴팀 총 킬뎃어시
                for(int i = 0; i<winTeamList.size(); i++) {
                    teamKills += winTeamList.get(i).getStats().getKills();
                    teamDeaths += winTeamList.get(i).getStats().getDeaths();
                    teamAssists += winTeamList.get(i).getStats().getAssists();
                }
                tvDetailWinTeamKill.setText(teamKills+"");
                tvDetailWinTeamDeath.setText(teamDeaths+"");
                tvDetailWinTeamAssist.setText(teamAssists+"");

                //진팀
                for(int i = 0; i<loseTeamList.size(); i++) {
                    teamKills += loseTeamList.get(i).getStats().getKills();
                    teamDeaths += loseTeamList.get(i).getStats().getDeaths();
                    teamAssists += loseTeamList.get(i).getStats().getAssists();
                }
                tvDetailLoseTeamKill.setText(teamKills+"");
                tvDetailLoseTeamDeath.setText(teamDeaths+"");
                tvDetailLoseTeamAssist.setText(teamAssists+"");


                //각 어댑터에 보내기
                adapter(winTeamList, loseTeamList, participantIdentities, duration);




            }

            @Override
            public void onFailure(Call<CMRespDto<ApiMatch>> call, Throwable t) {
                Log.d(TAG, "onFailure: 실행 실패" + t.getMessage());
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}