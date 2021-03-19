package com.cos.javagg.service;

import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.LoLDto;
import com.cos.javagg.model.api.ApiSummoner;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface SummonerApi {

    @GET("/info")
    Call<CMRespDto<LoLDto>> getInfo();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://113.198.238.68:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
