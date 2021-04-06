package com.cos.javagg.service;

import com.cos.javagg.dto.BoardUpdateDto;
import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.BoardDto;
import com.cos.javagg.dto.ReplyDto;
import com.cos.javagg.model.board.Board;
import com.cos.javagg.model.reply.Reply;

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



    @GET("/board/{page}")
    Call<CMRespDto<List<Board>>> findAll(@Path(value = "page") int page);

    @POST("/board/{id}")
    Call<CMRespDto<String>> save(@Path(value = "id") int id, @Body BoardDto postDto);

    @PUT("/board/{id}")
    Call<CMRespDto<Board>> update(@Path(value = "id") int id, @Body BoardUpdateDto boardUpdateDto);

    @DELETE("/board/{id}")
    Call<CMRespDto<String>> delete(@Path(value = "id") int id);

    @PUT("/board/count/{id}")
    Call<CMRespDto<Board>> count(@Path(value = "id") int id);

    @POST("/reply")
    Call<CMRespDto<Reply>> reply(@Body ReplyDto replyDto);

    @DELETE("/reply/{id}")
    Call<CMRespDto<String>> replyDelete(@Path(value = "id") int id);

    @POST("/likes/{boardId}")
    Call<CMRespDto<Integer>> likes(@Path(value = "boardId") int boardId, @Body int userId);

    @DELETE("/likes/{likesId}")
    Call<CMRespDto<String>> unlikes(@Path(value = "likesId") int boardId);

    @POST("/likesId/{boardId}")
    Call<CMRespDto<Integer>> likesId(@Path(value = "boardId") int boardId, @Body int userId);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.25.18:8090")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
