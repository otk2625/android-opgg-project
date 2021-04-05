package com.cos.javagg.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cos.javagg.R;
import com.cos.javagg.adapter.RankedAdapter;
import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.LoLDto;
import com.cos.javagg.dto.RankingDto;
import com.cos.javagg.model.api.ApiRanking;
import com.cos.javagg.model.api.ApiSummoner;
import com.cos.javagg.service.SummonerApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingFragment extends Fragment {
    private static final String TAG = "RankingFragment";
    private DrawerLayout mDrawerLayout;
    private ImageButton draw;
    private SummonerApi summonerApi;
    private Call<CMRespDto<ApiRanking>> call;
    private Call<CMRespDto<ApiSummoner>> call2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranked,container,false);

        mDrawerLayout=view.findViewById(R.id.drawerLayout);
        draw = view.findViewById(R.id.draw);

        drawL();    // 드로우레이아웃

        ArrayList<String> list = new ArrayList<>();     // 데이터담기
        for (int i=0; i<100; i++) {
            list.add(String.format("%d", i)) ;
        }
        //랭킹 데이터 가꼬오기
        ranking(view);




        return view;
    }

    private void ranking(View view) {
        summonerApi = SummonerApi.retrofit.create(SummonerApi.class);
        call = summonerApi.getRanking();

        call.enqueue(new Callback<CMRespDto<ApiRanking>>() {
            @Override
            public void onResponse(Call<CMRespDto<ApiRanking>> call, Response<CMRespDto<ApiRanking>> response) {
                CMRespDto<ApiRanking> cmRespDto = response.body();

                //랭킹 데이터
                ApiRanking apiRanking = cmRespDto.getData();

                //랭킹 내부 소환사들 apiRanking.entries; => 점수별로 순위 매겨야함
                Collections.sort(apiRanking.getEntries(), (a, b) -> (int) (b.getLeaguePoints() - a.getLeaguePoints()));

                List<RankingDto> rankingDtos = new ArrayList<>();


                for(int i = 0; i<100; i++){
                    RankingDto rankingDto = new RankingDto();

                    rankingDto.setRank(i+1);
                    rankingDto.setLeaguePoints(apiRanking.getEntries().get(i).getLeaguePoints());
                    rankingDto.setSummonerId(apiRanking.getEntries().get(i).getSummonerId());
                    rankingDto.setSummonerName(apiRanking.getEntries().get(i).getSummonerName());

                    rankingDtos.add(rankingDto);

                }


                //리사이클러뷰에 소환사 주기
                // 리사이클러뷰에 LinearLayoutManager 객체 지정.
                RecyclerView recyclerView = view.findViewById(R.id.rank_rc) ;
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())) ;

                // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
                RankedAdapter adapter = new RankedAdapter(rankingDtos) ;
                recyclerView.setAdapter(adapter) ;

            }

            @Override
            public void onFailure(Call<CMRespDto<ApiRanking>> call, Throwable t) {

            }
        });
    }


    public void drawL(){
        draw.setOnClickListener(v -> {
            mDrawerLayout.openDrawer(GravityCompat.START);
        });
    }
}