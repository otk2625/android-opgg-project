package com.cos.javagg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.cos.javagg.fragment.CommunityFragment;
import com.cos.javagg.fragment.RankingFragment;
import com.cos.javagg.fragment.SearchFragment;

import com.cos.javagg.listener.OnBackPressedListener;
import com.cos.javagg.model.board.Board;
import com.cos.javagg.model.user.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements OnBackPressedListener {

    private static final String TAG = "MainActivity2";
    private Context mContext = MainActivity.this;
    private BottomNavigationView bottomNavigationView;
    public static User loginUser;
    public static Board board;
    public static boolean noSummoner = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getHashKey();

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

            }
            //fragmanet 바꿔치기
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        });

    }

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }




}