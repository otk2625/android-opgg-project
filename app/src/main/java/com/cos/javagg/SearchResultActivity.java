package com.cos.javagg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cos.javagg.adapter.MatchListAdapter;
import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.LoLDto;
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

    private ImageView iv1;
    private CircleImageView view1;
    private RecyclerView rvMatchList;
    private MatchListAdapter matchListAdapter;
    private ImageView ivTier;
    private LinearLayoutManager manger;
    private SummonerApi summonerApi;
    private Call<CMRespDto<LoLDto>> call;
    private TextView tvSummornername, tvSummonerLevel;
    private ProgressDialog dialog; //pgb_search_result
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        findid();

        progressBar.setIndeterminate(false);
        progressBar.setProgress(100);
        dialog = new ProgressDialog(SearchResultActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("소환사 정보를 불러오는중 입니다.");

        dialog.show();



        Intent intent = getIntent();
        String summonerName = intent.getStringExtra("summonerName");
        Log.d(TAG, "onCreate: " + intent.getStringExtra("summonerName"));
        call = summonerApi.getInfo(summonerName);

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
    }

    protected void loadImages(long id) {
        // 이미지뷰 가져오기
        Glide
                .with(this)
                .load("http://ddragon.leagueoflegends.com/cdn/10.6.1/img/profileicon/"+id+".png")
                .centerCrop()
                .into(view1);

        ivTier.setImageResource(R.drawable.grandmaster);
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


                ApiSummoner apiSummoner = cmRespDto.getData().getApiSummoner();
                summonerInfoSetting(apiSummoner);

                ApiMatchEntry apiMatchEntry = cmRespDto.getData().getApiMatchEntry();
                apiMatchEntry.getMatches().get(0).getChampion();
                List<ApiMatch> apiMatch = cmRespDto.getData().getApiMatch();

                // 리스트에 어댑터 보내기
                adapter(apiMatchEntry, apiMatch);

                List<Team> teams = apiMatch.get(0).getTeams();


                Log.d(TAG, "onResponse: " + apiSummoner.toString());
                Log.d(TAG, "onResponse: 승리?" + teams.get(0).getWin());
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

}