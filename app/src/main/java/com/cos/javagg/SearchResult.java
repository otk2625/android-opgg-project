package com.cos.javagg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cos.javagg.adapter.MatchListAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchResult extends AppCompatActivity {
    private ImageView iv1;
    private CircleImageView view1;
    private RecyclerView rvMatchList;
    private MatchListAdapter matchListAdapter;
    private ImageView ivTier;
    private LinearLayoutManager manger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        findid();

        List<Integer> test = new ArrayList<>();
        test.add(1);
        test.add(2);



        adaper(test);
        loadImages();
    }

    private void findid() {
        view1 = (CircleImageView) findViewById(R.id.profile_image);
        rvMatchList = findViewById(R.id.rv_match_list);
        ivTier = findViewById(R.id.iv_tier);
    }

    protected void loadImages() {
        // 이미지뷰 가져오기
        Glide
                .with(this)
                .load("http://ddragon.leagueoflegends.com/cdn/10.6.1/img/profileicon/4529.png")
                .centerCrop()
                .into(view1);

        ivTier.setImageResource(R.drawable.grandmaster);
    }

    public void adaper(List<Integer> test){
        manger = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rvMatchList.setLayoutManager(manger);
        matchListAdapter = new MatchListAdapter(test);
        rvMatchList.setAdapter(matchListAdapter);
    }
}