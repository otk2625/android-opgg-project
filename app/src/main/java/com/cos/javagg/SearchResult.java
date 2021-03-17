package com.cos.javagg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cos.javagg.adapter.MatchListAdapter;
import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.model.ApiSummoner;
import com.cos.javagg.service.SummonerApi;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResult extends AppCompatActivity {
    private static final String TAG = "SearchResult";

    private ImageView iv1;
    private CircleImageView view1;
    private RecyclerView rvMatchList;
    private MatchListAdapter matchListAdapter;
    private ImageView ivTier;
    private LinearLayoutManager manger;
    private SummonerApi summonerApi;
    private Call<CMRespDto<ApiSummoner>> call;
    private TextView tvSummornername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        findid();

        List<Integer> test = new ArrayList<>();
        test.add(1);
        test.add(2);

        adapter(test);
        loadImages();

        infoFindByName();
    }

    private void findid() {
        view1 = (CircleImageView) findViewById(R.id.profile_image);
        rvMatchList = findViewById(R.id.rv_match_list);
        ivTier = findViewById(R.id.iv_tier);
        summonerApi = SummonerApi.retrofit.create(SummonerApi.class);
        call = summonerApi.getInfo();
        tvSummornername = findViewById(R.id.tv_summornername);
    }

    protected void loadImages() {
        // 이미지뷰 가져오기
        Glide
                .with(this)
                .load("http://ddragon.leagueoflegends.com/cdn/10.6.1/img/profileicon/4529.png")
                .centerCrop()
                .into(view1);

        ivTier.setImageResource(R.drawable.grandmaster);
    }

    public void adapter(List<Integer> test){
        manger = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rvMatchList.setLayoutManager(manger);
        matchListAdapter = new MatchListAdapter(test);
        rvMatchList.setAdapter(matchListAdapter);
    }

    public void infoFindByName(){
        Log.d(TAG, "infoFindByName: 실행됨");
        call.enqueue(new Callback<CMRespDto<ApiSummoner>>() {
            @Override
            public void onResponse(Call<CMRespDto<ApiSummoner>> call, Response<CMRespDto<ApiSummoner>> response) {
                CMRespDto<ApiSummoner> cmRespDto = response.body();
                ApiSummoner apiSummoner = cmRespDto.getData();

                Log.d(TAG, "onResponse: " + apiSummoner.toString());
                tvSummornername.setText(apiSummoner.getName());
            }

            @Override
            public void onFailure(Call<CMRespDto<ApiSummoner>> call, Throwable t) {
                Log.d(TAG, "onFailure: 실행 실패" + t.getMessage());
            }
        });
    }
}