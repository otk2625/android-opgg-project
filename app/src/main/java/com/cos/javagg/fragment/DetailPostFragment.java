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

import com.cos.javagg.MainActivity;
import com.cos.javagg.R;
import com.cos.javagg.adapter.CommunityAdapter;
import com.cos.javagg.adapter.ReplyAdapter;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.fontawesome.FontTextView;

public class DetailPostFragment  extends Fragment {
    private RecyclerView rvReplyList;
    private RecyclerView.LayoutManager replyLayoutManager;
    private ReplyAdapter replyAdapter;
    private FontTextView ftvAddBack;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity at = (MainActivity)container.getContext();

        View view = inflater.inflate(R.layout.fragment_detailpost,container,false);

        //어댑터 처리
        List<Integer> posts = new ArrayList<>();

        for (int i=0; i<20; i++){
            posts.add(i);
        }

        rvReplyList = (RecyclerView) view.findViewById(R.id.rv_reply_list);

        replyLayoutManager = new LinearLayoutManager(getActivity());
        rvReplyList.setLayoutManager(replyLayoutManager);

        replyAdapter = new ReplyAdapter(posts);
        rvReplyList.setAdapter(replyAdapter);

        ftvAddBack = view.findViewById(R.id.ftv_DetailPostback);
        ftvAddBack.setOnClickListener(v -> {
            at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CommunityFragment()).commit();
        });

        return view;
    }
}
