package com.cos.javagg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchResult extends AppCompatActivity {
    private ImageView iv1;
    private CircleImageView view1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        loadImages();
    }

    protected void loadImages() {
        // 이미지뷰 가져오기
        view1 = (CircleImageView) findViewById(R.id.profile_image);
        Glide
                .with(this)
                .load("http://ddragon.leagueoflegends.com/cdn/10.6.1/img/profileicon/4529.png")
                .centerCrop()
                .into(view1);
    }
}