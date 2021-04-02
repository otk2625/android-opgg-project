package com.cos.javagg.service;

import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.FaceBookLoginDto;
import com.cos.javagg.dto.JoinDto;
import com.cos.javagg.dto.LoginDto;
import com.cos.javagg.model.user.User;

import okhttp3.CookieJar;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    public static CookieJar cookieJar = null;


    @POST("/login")
    Call<CMRespDto<User>> getLogin(@Body LoginDto loginDto);

    @POST("/join")
    Call<CMRespDto<String>> getJoin(@Body JoinDto joinDto);

    @POST("/login/facebook")
    Call<CMRespDto<User>> getLoginFaceBook(@Body FaceBookLoginDto faceBookLoginDto);



//    Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://192.168.25.18:8080")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();
}
