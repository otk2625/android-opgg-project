package com.cos.javagg.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.javagg.LoginActivity;
import com.cos.javagg.MainActivity;
import com.cos.javagg.R;
import com.cos.javagg.adapter.CommunityAdapter;
import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.model.board.Board;
import com.cos.javagg.service.CommunityApi;

import java.util.List;

import info.androidhive.fontawesome.FontTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityFragment extends Fragment {
    private RecyclerView rvPostList;
    private RecyclerView.LayoutManager postLayoutManager;
    private CommunityAdapter communityAdapter;
    private FontTextView ftvPost, ftv_login;
    private static final String TAG = "CommunityFragment";
    private Button button;

    private CommunityApi communityApi;
    private Call<CMRespDto<List<Board>>> call;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity at = (MainActivity)container.getContext();

        View view = inflater.inflate(R.layout.fragment_community,container,false);
        init(view);

        //어댑터 처리
//        List<Integer> posts = new ArrayList<>();
//
//        for (int i=0; i<20; i++){
//            posts.add(i);
//        }


        communityApi = CommunityApi.retrofit.create(CommunityApi.class);
        call = communityApi.findAll();

        call.enqueue(new Callback<CMRespDto<List<Board>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<Board>>> call, Response<CMRespDto<List<Board>>> response) {
                CMRespDto<List<Board>> cmRespDto = response.body();
                List<Board> posts = cmRespDto.getData();

                //여기서 어댑터 전달
                communityAdapter = new CommunityAdapter(posts);
                postLayoutManager = new LinearLayoutManager(getActivity());

                rvPostList.setLayoutManager(postLayoutManager);
                rvPostList.setAdapter(communityAdapter);

            }

            @Override
            public void onFailure(Call<CMRespDto<List<Board>>> call, Throwable t) {

            }
        });


        listen(at);


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

            if (at.loginUser == null){
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(at, "현재 로그인중인 사람은 : " + at.loginUser.getUsername(), Toast.LENGTH_SHORT).show();
            }


        });
    }
}