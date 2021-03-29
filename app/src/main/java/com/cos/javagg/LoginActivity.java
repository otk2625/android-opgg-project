package com.cos.javagg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.LoginDto;
import com.cos.javagg.model.user.User;
import com.cos.javagg.service.AuthApi;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.val;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private TextView tv_join, tvForgetPassword;
    private EditText et_userid, et_password;
    private Button btn_login;
    private AuthApi authApi;
    private Call<CMRespDto<User>> call;
    private LoginDto loginDto;
    private MainActivity mainActivity;
    public static Context mContext;

//    private HttpClient httpClient;
//    private HttpPost httpPost;
//    private HttpResponse httpResponse;
//    private HttpEntity httpEntity;

    // post 매핑
    private HttpLoggingInterceptor logging;
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findId();
        listener();
        toolbarsetting();

        tvForgetPassword.setText(Html.fromHtml( "<font color=\"#2a7de2\"><u>비밀번호를 잊으셨나요</u></font>"));
        tv_join.setText(Html.fromHtml( "<font color=\"#2a7de2\"><u>회원가입하기</u></font>"));


        logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(),
                new SharedPrefsCookiePersistor(this));
        OkHttpClient client = new OkHttpClient.Builder().cookieJar(cookieJar).addInterceptor(logging).build();
        httpClient.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://113.198.238.68:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authApi = retrofit.create(AuthApi.class);

    }


    public void findId(){
        tv_join = findViewById(R.id.tv_join);
        et_userid = findViewById(R.id.et_userid);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        tvForgetPassword = findViewById(R.id.tv_forgetpassword);
       // authApi = AuthApi.retrofit.create(AuthApi.class);
    }

    public void listener(){
        btn_login.setOnClickListener(view -> {
            //여기서 로그인 할거임
            loginDto = new LoginDto();
            if(et_userid.getText().toString().length() != 0 && et_password.getText().toString().length() != 0) {
                loginDto.setUsername(et_userid.getText() + "");
                loginDto.setPassword(et_password.getText() + "");

                call = authApi.getLogin(loginDto);
                Log.d(TAG, "listener: 실행됨");
                call.enqueue(new Callback<CMRespDto<User>>() {
                    @Override
                    public void onResponse(Call<CMRespDto<User>> call, Response<CMRespDto<User>> response) {
                        CMRespDto<User> cmRespDto = response.body();
                       User test = cmRespDto.getData();
                        Log.d(TAG, "onResponse: 실행됨");
                        Log.d(TAG, "onResponse: cmRespDto.getResultCode() : " + cmRespDto.getResultCode());
                       if (cmRespDto.getResultCode() == 1){
                           Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                           Gson gson = new Gson();
                           Intent newIntent = new Intent(LoginActivity.this, MainActivity.class);
                           User user = cmRespDto.getData();
                           newIntent.putExtra("auth",gson.toJson(user));

                           startActivity(newIntent);
                           finish();
                       } else{
                           Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 확인해 주세종", Toast.LENGTH_SHORT).show();
                       }
                    }

                    @Override
                    public void onFailure(Call<CMRespDto<User>> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                });

            } else{
                Toast.makeText(this, "ID와 비밀번호가 비었는지 확인하세요", Toast.LENGTH_SHORT).show();
            }
        });

        tv_join.setOnClickListener(view -> {
            Intent intent = new Intent(this, JoinActivity.class);
            startActivity(intent);
        });


    }

    public void toolbarsetting() {
        //toolBar를 통해 App Bar 생성
        Toolbar toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);

        //App Bar의 좌측 영영에 Drawer를 Open 하기 위한 Incon 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}