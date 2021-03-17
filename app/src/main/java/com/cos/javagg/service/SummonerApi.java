package com.cos.javagg.service;

import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.model.ApiSummoner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface SummonerApi {

    @GET("/info")
    Call<CMRespDto<ApiSummoner>> getInfo();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://113.198.238.68:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
