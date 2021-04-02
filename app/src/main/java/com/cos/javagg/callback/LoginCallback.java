package com.cos.javagg.callback;

import android.os.Bundle;
import android.util.Log;

import com.cos.javagg.dto.FaceBookLoginDto;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;

import org.json.JSONObject;

public class LoginCallback implements FacebookCallback<LoginResult> {

    private static final String TAG = "LoginCallback";

    @Override
    public void onSuccess(LoginResult loginResult)
    {
        Log.e("Callback :: ", "onSuccess");
        requestMe(loginResult.getAccessToken());
    }

    @Override
    public void onCancel()
    {
        Log.e("Callback :: ", "onCancel");
    }

    @Override
    public void onError(FacebookException error)
    {
        Log.e("Callback :: ", "onError : " + error.getMessage());
    }

    // 사용자 정보 요청
    public void requestMe(AccessToken token)
    {
        GraphRequest graphRequest = GraphRequest.newMeRequest(token,
                new GraphRequest.GraphJSONObjectCallback()
                {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response)
                    {
                        Log.e("result",object.toString());
                        Gson gson = new Gson();

                        FaceBookLoginDto faceBookLoginDto = gson.fromJson(object.toString(),FaceBookLoginDto.class);
                        Log.d(TAG, "onCompleted: 페북 받아와지나" + faceBookLoginDto.toString());

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

}