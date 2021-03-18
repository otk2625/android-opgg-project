package com.cos.javagg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.javagg.R;
import com.cos.javagg.adapter.RankedAdapter;

import java.util.ArrayList;

public class RankingFragment extends Fragment {
    private DrawerLayout mDrawerLayout;
    private ImageButton draw;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranked,container,false);

        mDrawerLayout=view.findViewById(R.id.drawerLayout);
        draw = view.findViewById(R.id.draw);

        drawL();    // 드로우레이아웃

        ArrayList<String> list = new ArrayList<>();     // 데이터담기
        for (int i=0; i<100; i++) {
            list.add(String.format("%d", i)) ;
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = view.findViewById(R.id.rank_rc) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        RankedAdapter adapter = new RankedAdapter(list) ;
        recyclerView.setAdapter(adapter) ;


        return view;
    }



    public void drawL(){
        draw.setOnClickListener(v -> {
            mDrawerLayout.openDrawer(GravityCompat.START);
        });
    }
}