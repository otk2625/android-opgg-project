package com.cos.javagg.service;

import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.BoardDto;
import com.cos.javagg.model.board.Board;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommunityApi {



    @GET("/board")
    Call<CMRespDto<List<Board>>> findAll();

    @POST("/board/{id}")
    Call<CMRespDto<String>> save(@Path(value = "id") int id, @Body BoardDto postDto);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://113.198.238.68:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
