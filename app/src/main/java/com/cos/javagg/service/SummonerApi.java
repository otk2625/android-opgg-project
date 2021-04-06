package com.cos.javagg.service;

import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.LoLDto;
import com.cos.javagg.model.api.ApiMatch;
import com.cos.javagg.model.api.ApiRanking;
import com.cos.javagg.model.api.ApiSummoner;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SummonerApi {

    @GET("/info/{summonerName}")
    Call<CMRespDto<LoLDto>> getInfo(@Path(value = "summonerName") String summonerName);

    @GET("/match/{matchGameId}")
    Call<CMRespDto<ApiMatch>> getMatchGameId(@Path(value = "matchGameId") String matchGameId);

    @GET("/rank")
    Call<CMRespDto<ApiRanking>> getRanking();

    @GET("/ranksummoner/{summonerName}")
    Call<CMRespDto<ApiSummoner>> getRankSummoner(@Path(value = "summonerName") String summonerName);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.25.18:8090")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
