package com.cos.javagg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cos.javagg.fragment.ChampionFragment;
import com.cos.javagg.fragment.CommunityFragment;
import com.cos.javagg.fragment.LoginFragment;
import com.cos.javagg.fragment.MakePostFragment;
import com.cos.javagg.fragment.RankingFragment;
import com.cos.javagg.fragment.SearchFragment;

import com.cos.javagg.listener.OnBackPressedListener;
import com.cos.javagg.model.user.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import info.androidhive.fontawesome.FontTextView;

public class MainActivity extends AppCompatActivity implements OnBackPressedListener {

    private static final String TAG = "MainActivity2";
    private Context mContext = MainActivity.this;
    private BottomNavigationView bottomNavigationView;
    private static User loginUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        Intent intent = getIntent();
        Log.d(TAG, "onCreate: " + intent.getStringExtra("auth"));

        Gson gson = new Gson();

        loginUser = gson.fromJson(intent.getStringExtra("auth"), User.class);
        if (loginUser != null){
            Log.d(TAG, "onCreate: 로그인한 유 : " + loginUser.toString());
            Toast.makeText(mContext, "로그인 성공", Toast.LENGTH_SHORT).show();
        }



        //최초 화면
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            switch (item.getItemId())  {
                case  R.id.bottom_search:
                    selectedFragment = new SearchFragment();
                    break;
                case R.id.bottom_community :
                    selectedFragment = new CommunityFragment();
                    break;
                case R.id.bottom_ranking :
                    selectedFragment = new RankingFragment();
                    break;
                case R.id.bottom_login :
                    selectedFragment = new LoginFragment();
                    break;

            }
            //fragmanet 바꿔치기
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        });

    }



}