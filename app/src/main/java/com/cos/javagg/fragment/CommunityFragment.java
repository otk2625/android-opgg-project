package com.cos.javagg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.javagg.R;
import com.cos.javagg.adapter.CommunityAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {
    private RecyclerView rvPostList;
    private RecyclerView.LayoutManager postLayoutManager;
    private CommunityAdapter communityAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community,container,false);

        List<Integer> posts = new ArrayList<>();

        for (int i=0; i<20; i++){
            posts.add(i);
        }

        rvPostList = (RecyclerView) view.findViewById(R.id.rv_post_list);

        postLayoutManager = new LinearLayoutManager(getActivity());
        rvPostList.setLayoutManager(postLayoutManager);

        communityAdapter = new CommunityAdapter(posts);
        rvPostList.setAdapter(communityAdapter);

        return view;
    }
}