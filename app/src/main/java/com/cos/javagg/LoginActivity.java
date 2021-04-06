package com.cos.javagg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cos.javagg.callback.LoginCallback;
import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.FaceBookLoginDto;
import com.cos.javagg.dto.LoginDto;
import com.cos.javagg.model.user.User;
import com.cos.javagg.service.AuthApi;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Arrays;

import okhttp3.CookieJar;
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
    private Call<CMRespDto<User>> callFaceBook;
    private LoginDto loginDto;
    private MainActivity mainActivity;
    public Context mContext = LoginActivity.this;

    //페이스북 로그인
    private LoginButton btn_Facebook_Login;
    private CallbackManager callbackManager;
    private LoginCallback loginCallack;

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
                .baseUrl("http://192.168.25.18:8090")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authApi = retrofit.create(AuthApi.class);


        facebooklogin();

    }

    private void facebooklogin() {
        //페이스북 로그인
        callbackManager = CallbackManager.Factory.create();
        loginCallack = new LoginCallback();

        btn_Facebook_Login.setOnClickListener(view -> {
            btn_Facebook_Login.setReadPermissions(Arrays.asList("public_profile", "email"));
            //btn_Facebook_Login.registerCallback(callbackManager, loginCallack);
            // Callback registration
            btn_Facebook_Login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    Log.d(TAG, "onSuccess: " + loginResult.toString());
                    // App code
                    Log.e(TAG, "FaceBook 로그인 성공 ");
                    /** 페이스북 로그인 성공시 계정에 관련된 정보는 별도로 가지고 와야함 **/
                    GraphRequest request = new GraphRequest().newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                Log.e(TAG, "FaceBook onSuccess : " + object.getString("email"));
                                Log.e(TAG, "FaceBook 전체 : " + object.toString());
                                FaceBookLoginDto faceBookLoginDto = FaceBookLoginDto.builder()
                                        .email(object.getString("email"))
                                        .id(object.getLong("id"))
                                        .name(object.getString("name"))
                                        .build();
                                Log.d(TAG, "onCompleted: 확인해보자 : " + faceBookLoginDto.toString());
                                //여기서 이 값을 DB에 저장하고, 로그인 상태를 유지시켜야 한다.

                                call = authApi.getLoginFaceBook(faceBookLoginDto);
                                call.enqueue(new Callback<CMRespDto<User>>() {
                                    @Override
                                    public void onResponse(Call<CMRespDto<User>> call, Response<CMRespDto<User>> response) {
                                        //로그인 정보 받아
                                        CMRespDto<User> cmRespDto = response.body();

                                        if(cmRespDto.getResultCode() == 2){
                                            Toast.makeText(LoginActivity.this, "최초 사용자입니다. 자동 회원가입 후 로그인 합니다", Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, "onResponse: 로그인 정보 : " + cmRespDto.getData().toString());
                                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                                            builder.setTitle("").setMessage("최초 사용자입니다. 자동 회원가입 후 로그인 합니다");

                                            AlertDialog alertDialog = builder.create();
                                            alertDialog.show();

                                            Gson gson = new Gson();
                                            Intent newIntent = new Intent(LoginActivity.this, MainActivity.class);
                                            User user = cmRespDto.getData();
                                            newIntent.putExtra("auth",gson.toJson(user));

                                            LoginManager.getInstance().logOut();

                                            startActivity(newIntent);
                                            finish();

                                        }else{
                                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                                            builder.setTitle("").setMessage("로그인 완료");

                                            AlertDialog alertDialog = builder.create();
                                            alertDialog.show();

                                            Gson gson = new Gson();
                                            Intent newIntent = new Intent(LoginActivity.this, MainActivity.class);
                                            User user = cmRespDto.getData();
                                            newIntent.putExtra("auth",gson.toJson(user));

                                            LoginManager.getInstance().logOut();

                                            startActivity(newIntent);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<CMRespDto<User>> call, Throwable t) {

                                    }
                                });

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });

                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email");
                    request.setParameters(parameters);
                    request.executeAsync();

                }

                @Override
                public void onCancel() {
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                }
            });
        });

    }


    public void findId(){
        tv_join = findViewById(R.id.tv_join);
        et_userid = findViewById(R.id.et_userid);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        tvForgetPassword = findViewById(R.id.tv_forgetpassword);
       // authApi = AuthApi.retrofit.create(AuthApi.class);

        btn_Facebook_Login = findViewById(R.id.facebook_login);
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
                       if (cmRespDto.getData() != null){
                           AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                           builder.setTitle("").setMessage("로그인 완료");

                           builder.setNegativeButton("닫기",(dialogInterface, i) -> {
                               Gson gson = new Gson();
                               Intent newIntent = new Intent(LoginActivity.this, MainActivity.class);
                               User user = cmRespDto.getData();
                               newIntent.putExtra("auth",gson.toJson(user));

                               startActivity(newIntent);
                               finish();
                           });

                           AlertDialog alertDialog = builder.create();
                           alertDialog.show();


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

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}