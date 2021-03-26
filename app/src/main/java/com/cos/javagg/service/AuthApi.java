package com.cos.javagg.service;

import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.LoLDto;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthApi {

    @GET("/login")
    Call<CMRespDto<LoLDto>> getInfo(@Path(value = "summonerName") String summonerName);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.25.18:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
