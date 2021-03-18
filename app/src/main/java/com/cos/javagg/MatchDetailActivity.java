package com.cos.javagg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.cos.javagg.adapter.MatchDetailLossListAdapter;
import com.cos.javagg.adapter.MatchDetailWinListAdapter;
import com.cos.javagg.adapter.MatchListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MatchDetailActivity extends AppCompatActivity {
    private static final String TAG = "MatchDetailActivity";
    private RecyclerView rvDetailWin, rvDetailLoss;
    private LinearLayoutManager manger, manger2;
    private MatchDetailWinListAdapter matchDetailWinListAdapter;
    private MatchDetailLossListAdapter matchDetailLossListAdapter;
    private ImageView ivInfoDetailBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        List<Integer> test = new ArrayList<>();
        test.add(1);
        test.add(2);
        test.add(3);
        test.add(4);
        test.add(5);

        findId();
        adapter(test);
        listener();
    }

    private void findId() {
        rvDetailWin = findViewById(R.id.rv_detail_win);
        rvDetailLoss = findViewById(R.id.rv_detail_lose);
        ivInfoDetailBack = findViewById(R.id.iv_info_detail_back);
    }

    public void adapter(List<Integer> test){
        manger = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        manger2 = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rvDetailWin.setLayoutManager(manger);
        rvDetailLoss.setLayoutManager(manger2);
        matchDetailWinListAdapter = new MatchDetailWinListAdapter(test);
        matchDetailLossListAdapter = new MatchDetailLossListAdapter(test);
        rvDetailWin.setAdapter(matchDetailWinListAdapter);
        rvDetailLoss.setAdapter(matchDetailLossListAdapter);
    }
    public void listener(){
        ivInfoDetailBack.setOnClickListener(v -> {
            Log.d(TAG, "listener: 실행됨");
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}