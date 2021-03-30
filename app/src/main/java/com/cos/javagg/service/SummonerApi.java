package com.cos.javagg.service;

import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.LoLDto;
import com.cos.javagg.model.api.ApiMatch;

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



    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://113.198.238.68:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
