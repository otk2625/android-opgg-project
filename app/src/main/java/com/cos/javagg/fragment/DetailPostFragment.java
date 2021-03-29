package com.cos.javagg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    private Button btn_delete, btn_update;
    private  MainActivity at;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        at = (MainActivity)container.getContext();

        View view = inflater.inflate(R.layout.fragment_detailpost,container,false);

        findById(view);

        //어댑터 처리
        List<Integer> posts = new ArrayList<>();

        for (int i=0; i<20; i++){
            posts.add(i);
        }

        rvReplyList = (RecyclerView) view.findViewById(R.id.rv_reply_list);
        rvReplyList.setLayoutManager(replyLayoutManager);
        rvReplyList.setAdapter(replyAdapter);


        //사용자 있는지 체크
        if(at.loginUser != null){
            btn_delete.setVisibility(View.VISIBLE);
            btn_update.setVisibility(View.VISIBLE);
        }

        listener();

        return view;
    }

    public void findById(View view){
        btn_delete = view.findViewById(R.id.btn_delete);
        btn_update = view.findViewById(R.id.btn_update);
        replyAdapter = new ReplyAdapter(null);
        replyLayoutManager = new LinearLayoutManager(getActivity());

        ftvAddBack = view.findViewById(R.id.ftv_DetailPostback);
    }

    public void listener(){
        //뒤로가기 버튼
        ftvAddBack.setOnClickListener(v -> {
            at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CommunityFragment()).commit();
        });
    }
}
