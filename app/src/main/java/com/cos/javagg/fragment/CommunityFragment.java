package com.cos.javagg.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.javagg.LoginActivity;
import com.cos.javagg.MainActivity;
import com.cos.javagg.R;
import com.cos.javagg.SearchResultActivity;
import com.cos.javagg.adapter.CommunityAdapter;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.fontawesome.FontTextView;

public class CommunityFragment extends Fragment {
    private RecyclerView rvPostList;
    private RecyclerView.LayoutManager postLayoutManager;
    private CommunityAdapter communityAdapter;
    private FontTextView ftvPost, ftv_login;
    private static final String TAG = "CommunityFragment";
    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity at = (MainActivity)container.getContext();

        View view = inflater.inflate(R.layout.fragment_community,container,false);

        //어댑터 처리
        List<Integer> posts = new ArrayList<>();

        for (int i=0; i<20; i++){
            posts.add(i);
        }
        init(view);
        listen(at);

        postLayoutManager = new LinearLayoutManager(getActivity());
        communityAdapter = new CommunityAdapter(posts);



        rvPostList.setLayoutManager(postLayoutManager);
        rvPostList.setAdapter(communityAdapter);



        return view;
    }

    public void init(View view){
        rvPostList = (RecyclerView) view.findViewById(R.id.rv_post_list);
        ftvPost = view.findViewById(R.id.ftv_post);
        button = view.findViewById(R.id.btn_favorite);
        ftv_login = view.findViewById(R.id.ftv_login);
    }

    public void listen(MainActivity at){
        ftvPost.setOnClickListener(v -> {
            at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MakePostFragment()).commit();
        });

        button.setOnClickListener(v -> {
            at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DetailPostFragment()).commit();
        });

        ftv_login.setOnClickListener(v-> {


            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }
}