package com.cos.javagg.service;

import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.JoinReqDto;
import com.cos.javagg.dto.LoginDto;
import com.cos.javagg.model.user.User;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthApi {
    public static CookieJar cookieJar = null;


    @POST("/login")
    Call<CMRespDto<User>> getLogin(@Body LoginDto loginDto);

    @POST("/join")
    Call<CMRespDto<String>> getJoin(@Body JoinReqDto joinReqDto);



//    Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://192.168.25.18:8080")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();
}
