package com.cos.javagg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.JoinDto;
import com.cos.javagg.model.user.User;
import com.cos.javagg.service.AuthApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JoinActivity extends AppCompatActivity {
    private static final String TAG = "JoinActivity";
    private Button btn_join;
    private AuthApi authApi;
    private Call<CMRespDto<String>> call;
    private Retrofit retrofit;
    private ImageButton join_close;
    private EditText et_email, et_username, et_password;
    private LoginActivity loginActivity;
    private static boolean isJoin = false;
    public JoinActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        findById();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.25.18:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authApi = retrofit.create(AuthApi.class);


        listener();



    }

    public void findById(){
        join_close = findViewById(R.id.close);
        btn_join = findViewById(R.id.btn_join);
        et_email = findViewById(R.id.et_email);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
    }

    public void listener(){
        join_close.setOnClickListener(v -> {
            this.finish();
        });

        btn_join.setOnClickListener(v -> {
            //회원가입 완료시
            JoinDto joinDto = new JoinDto();
            if(et_username.getText().toString().length() != 0 && et_password.getText().toString().length() != 0
                    && et_email.getText().toString().length() != 0) {
                joinDto.setUsername(et_username.getText() + "");
                joinDto.setEmail(et_email.getText() + "");
                joinDto.setPassword(et_password.getText() + "");

                call = authApi.getJoin(joinDto);

                call.enqueue(new Callback<CMRespDto<String>>() {
                    @Override
                    public void onResponse(Call<CMRespDto<String>> call, Response<CMRespDto<String>> response) {
                        CMRespDto<String> cmRespDto = response.body();

                        if (cmRespDto.getResultCode() == 1) {
                            //성공
                            Toast.makeText((LoginActivity)loginActivity.mContext, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        } else {
                            //실패
                            Toast.makeText(JoinActivity.this, "아이디 중복", Toast.LENGTH_SHORT).show();
                        }

                        isJoin = true;

                        finish();

                    }

                    @Override
                    public void onFailure(Call<CMRespDto<String>> call, Throwable t) {
                        Toast.makeText(JoinActivity.this, "네트워크 연결 확인!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else{
                Toast.makeText(JoinActivity.this, "아이디, 비밀번호, 이메일을 다 적었는지 확인!", Toast.LENGTH_SHORT).show();
            }

        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

        if(isJoin == true){
            Toast.makeText((LoginActivity)loginActivity.mContext, "회원가입 성공", Toast.LENGTH_SHORT).show();
            isJoin = false;
        }

    }

}