package com.cos.javagg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cos.javagg.adapter.MatchListAdapter;
import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.LoLDto;
import com.cos.javagg.model.api.ApiEntry;
import com.cos.javagg.model.api.ApiMatch;
import com.cos.javagg.model.api.ApiMatchEntry;
import com.cos.javagg.model.api.ApiSummoner;
import com.cos.javagg.model.detail.Team;
import com.cos.javagg.service.SummonerApi;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {
    private static final String TAG = "SearchResult";

    private ImageView iv_tier;
    private CircleImageView view1;
    private RecyclerView rvMatchList;
    private MatchListAdapter matchListAdapter;
    private ImageView ivTier;
    private LinearLayoutManager manger;
    private SummonerApi summonerApi;
    private Call<CMRespDto<LoLDto>> call;
    private TextView tvSummornername, tvSummonerLevel, tvRankType, tvTier, tvTierPoint, tvRankWin, tvRankLoss, tvOdds, tvPersent;
    private CheckTypesTask task;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        findid();

        Intent intent = getIntent();
        String summonerName = intent.getStringExtra("summonerName");
        Log.d(TAG, "onCreate: " + intent.getStringExtra("summonerName"));
        call = summonerApi.getInfo(summonerName);


        task = new CheckTypesTask();

        infoFindByName(summonerName);
        toolbarsetting();



    }

    private void findid() {
        view1 = (CircleImageView) findViewById(R.id.profile_image);
        rvMatchList = findViewById(R.id.rv_match_list);
        ivTier = findViewById(R.id.iv_tier);
        summonerApi = SummonerApi.retrofit.create(SummonerApi.class);
        tvSummornername = findViewById(R.id.tv_summornername);
        tvSummonerLevel = findViewById(R.id.tv_summonerLevel);
        progressBar = findViewById(R.id.pgb_search_result);


        //개인 랭크 관련
        tvRankType = findViewById(R.id.tv_rankType);
        tvTier = findViewById(R.id.tv_tier);
        tvTierPoint = findViewById(R.id.tv_tierpoint);
        tvRankWin = findViewById(R.id.tv_rankwin);
        tvRankLoss = findViewById(R.id.tv_rankloss);
        tvOdds = findViewById(R.id.tv_odds);
        iv_tier =  findViewById(R.id.iv_tier);
        tvPersent = findViewById(R.id.tv_persent);
    }

    protected void loadImages(long id) {
        // 이미지뷰 가져오기
        Glide
                .with(this)
                .load("http://ddragon.leagueoflegends.com/cdn/11.6.1/img/profileicon/"+id+".png")
                .centerCrop()
                .into(view1);

        ivTier.setImageResource(R.drawable.unranked);
    }

    public void adapter(ApiMatchEntry apiMatchEntry, List<ApiMatch> apiMatch){
        manger = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rvMatchList.setLayoutManager(manger);
        matchListAdapter = new MatchListAdapter(apiMatchEntry, apiMatch);
        rvMatchList.setAdapter(matchListAdapter);
    }

    public void infoFindByName(String summonerName){
        call.enqueue(new Callback<CMRespDto<LoLDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<LoLDto>> call, Response<CMRespDto<LoLDto>> response) {
                CMRespDto<LoLDto> cmRespDto = response.body();

                if(cmRespDto.getResultCode() == 1){
                    progressBar.setIndeterminate(false);
                    progressBar.setProgress(100);

                    task.execute();

                    ApiSummoner apiSummoner = cmRespDto.getData().getApiSummoner();
                    summonerInfoSetting(apiSummoner);

                    ApiMatchEntry apiMatchEntry = cmRespDto.getData().getApiMatchEntry();
                    apiMatchEntry.getMatches().get(0).getChampion();
                    List<ApiMatch> apiMatch = cmRespDto.getData().getApiMatch();

                    // 리스트에 어댑터 보내기
                    adapter(apiMatchEntry, apiMatch);

                    //랭크 정보
                    List<ApiEntry> apiEntries = cmRespDto.getData().getApiEntries();
                    Log.d(TAG, "onResponse: apiEntries : " + apiEntries);
                    if (apiEntries.isEmpty() == true){
                        Toast.makeText(SearchResultActivity.this, "비었다리", Toast.LENGTH_SHORT).show();
                    }else{
                        rankInfo(apiEntries.get(0));
                    }
                } else{
                    Toast.makeText(SearchResultActivity.this, "없는 소환사입니다 다시 확인바랍니다", Toast.LENGTH_SHORT).show();

                    MainActivity.noSummoner = true;

                    finish();
                }






//                if (apiEntries == null){
//                    rankInfo(new ApiEntry());
//                }else{
//                    rankInfo(apiEntries.get(0));
//                }



            }

            @Override
            public void onFailure(Call<CMRespDto<LoLDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: 실행 실패" + t.getMessage());
            }
        });
    }

    private void summonerInfoSetting(ApiSummoner apiSummoner) {
        tvSummornername.setText(apiSummoner.getName());
        tvSummonerLevel.setText(apiSummoner.getSummonerLevel()+"");

        loadImages(apiSummoner.getProfileIconId());
    }

    public void rankInfo(ApiEntry apiEntry){
        if (apiEntry.getQueueType().equals("RANKED_SOLO_5x5")){
            tvRankType.setText("SOLO RANK");
        } else{
            tvRankType.setText("UnRank");
        }


        if(apiEntry.getTier().equals("CHALLENGER")){
            iv_tier.setImageResource(R.drawable.challenger);
            tvTier.setText("Challenger");
            rankSetting(apiEntry);
        } else if(apiEntry.getTier().equals("GRANDMASTER")){
            iv_tier.setImageResource(R.drawable.grandmaster);
            tvTier.setText("Grandmaster");
            rankSetting(apiEntry);
        } else if(apiEntry.getTier().equals("MASTER")){
            iv_tier.setImageResource(R.drawable.master);
            tvTier.setText("Master");
            rankSetting(apiEntry);
        } else if(apiEntry.getTier().equals("DIAMOND")) {
            iv_tier.setImageResource(R.drawable.diamond);
            tvTier.setText("Diamond");
            rankSetting(apiEntry);
        } else if(apiEntry.getTier().equals("PLATINUM")){
            iv_tier.setImageResource(R.drawable.platinum);
            tvTier.setText("Platinum");
            rankSetting(apiEntry);
        } else if(apiEntry.getTier().equals("GOLD")){
            iv_tier.setImageResource(R.drawable.gold);
            tvTier.setText("Gold");
            rankSetting(apiEntry);
        } else if(apiEntry.getTier().equals("SILVER")){
            iv_tier.setImageResource(R.drawable.silver);
            tvTier.setText("Silver");
            rankSetting(apiEntry);
        } else if(apiEntry.getTier().equals("BRONZE")){
            iv_tier.setImageResource(R.drawable.bronze);
            tvTier.setText("Bronze");
            rankSetting(apiEntry);
        } else {
            iv_tier.setImageResource(R.drawable.unranked);
            tvTier.setText("Unranked");
            tvTierPoint.setText("-");
            tvRankWin.setText("-");
            tvRankLoss.setText("-");
            tvOdds.setText(" ");
            tvPersent.setText(" ");
        }

    }

    public void rankSetting(ApiEntry apiEntry){
        tvTierPoint.setText(apiEntry.getLeaguePoints()+"");
        tvRankWin.setText(apiEntry.getWins()+"");
        tvRankLoss.setText(apiEntry.getLosses()+"");

        int win = (int)apiEntry.getWins();
        int loss = (int)apiEntry.getLosses();
        double odd = win*100/(win+loss);

        tvOdds.setText(odd+"");
    }

    public void toolbarsetting() {
        //toolBar를 통해 App Bar 생성
        Toolbar toolbar = findViewById(R.id.toolbar_search_result);
        setSupportActionBar(toolbar);

        //App Bar의 좌측 영영에 Drawer를 Open 하기 위한 Incon 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public class CheckTypesTask extends AsyncTask<Void,Void,Void>{
        private ProgressDialog dialog = new ProgressDialog(SearchResultActivity.this); //pgb_search_result

        @Override
        protected void onPreExecute() {
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("소환사 정보를 불러오는중 입니다.");

            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
           try {

               for (int i =0; i< 5; i++){
                   Thread.sleep(1000);
               }

           } catch (InterruptedException e){
               e.printStackTrace();
           }
           return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }

}