package com.cos.javagg.service;

import com.cos.javagg.dto.BoardUpdateDto;
import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.BoardDto;
import com.cos.javagg.dto.ReplyDto;
import com.cos.javagg.model.board.Board;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CommunityApi {



    @GET("/board")
    Call<CMRespDto<List<Board>>> findAll();

    @POST("/board/{id}")
    Call<CMRespDto<String>> save(@Path(value = "id") int id, @Body BoardDto postDto);

    @PUT("/board/{id}")
    Call<CMRespDto<Board>> update(@Path(value = "id") int id, @Body BoardUpdateDto boardUpdateDto);

    @DELETE("/board/{id}")
    Call<CMRespDto<String>> delete(@Path(value = "id") int id);

    @PUT("/board/count/{id}")
    Call<CMRespDto<Board>> count(@Path(value = "id") int id);

    @POST("/reply")
    Call<CMRespDto<String>> reply(@Body ReplyDto replyDto);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.25.18:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
