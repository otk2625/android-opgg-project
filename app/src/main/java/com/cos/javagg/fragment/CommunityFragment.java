package com.cos.javagg.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.fontawesome.FontTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView rvPostList;
    private RecyclerView.LayoutManager postLayoutManager;
    private DrawerLayout dl_community;
    private NavigationView nv_community;
    private CommunityAdapter communityAdapter;
    private FontTextView ftvPost, ftv_search;
    private static final String TAG = "CommunityFragment";
    private Button btn_favorite, btn_new, btn_top;
    private MainActivity at;
    private CommunityApi communityApi;
    private Call<CMRespDto<List<Board>>> call;
    private List<Board> boards;
    private List<Board> addBoards;
    private int page = 0;
    private boolean isLast=false;

    //페이징
    private boolean isLoading = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        at = (MainActivity)container.getContext();

        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_community,container,false);
        init(view);
        toolbarSetting(view);

        //어댑터 처리
//        List<Integer> posts = new ArrayList<>();
//
//        for (int i=0; i<20; i++){
//            posts.add(i);
//        }


        communityApi = CommunityApi.retrofit.create(CommunityApi.class);


        call = communityApi.findAll(page);
        call.enqueue(new Callback<CMRespDto<List<Board>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<Board>>> call, Response<CMRespDto<List<Board>>> response) {
                CMRespDto<List<Board>> cmRespDto = response.body();
                boards = cmRespDto.getData();

                //좋아요 로직
                if(at.loginUser != null){
                    for (int i =0; i<boards.size(); i++){
                        int likeCount = boards.get(i).getLikes().size();
                        boards.get(i).setLikeCount(likeCount);

                        for(int j = 0; j<boards.get(i).getLikes().size(); j++){

                            if( boards.get(i).getLikes().get(j).getUser().getId() == at.loginUser.getId()) {
                                boards.get(i).setLikeState(true);

                            }
                        }

                    }
                }


                //여기서 어댑터 전달
                communityAdapter = new CommunityAdapter(boards);
                postLayoutManager = new LinearLayoutManager(getActivity());

                rvPostList.setLayoutManager(postLayoutManager);
                rvPostList.setAdapter(communityAdapter);

            }

            @Override
            public void onFailure(Call<CMRespDto<List<Board>>> call, Throwable t) {

            }
        });


        listener(at);

        initScrollListener();

        return view;
    }

    private void initScrollListener() {
        rvPostList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == boards.size() - 1) {
                        //리스트 마지막
                        if(boards.size()>9){

                            page = page + 10;
                            Log.d(TAG, "onScrolled: page값" + page);

                            call = communityApi.findAll(page);
                            call.enqueue(new Callback<CMRespDto<List<Board>>>() {
                                @Override
                                public void onResponse(Call<CMRespDto<List<Board>>> call, Response<CMRespDto<List<Board>>> response) {
                                    CMRespDto<List<Board>> cmRespDto = response.body();

                                    if(cmRespDto.getResultCode() == 1){

                                        addBoards = cmRespDto.getData();

                                        //좋아요 로직
                                        if(at.loginUser != null){
                                            for (int i =0; i<addBoards.size(); i++){
                                                int likeCount = addBoards.get(i).getLikes().size();
                                                addBoards.get(i).setLikeCount(likeCount);

                                                for(int j = 0; j<addBoards.get(i).getLikes().size(); j++){

                                                    if( addBoards.get(i).getLikes().get(j).getUser().getId() == at.loginUser.getId()) {
                                                        addBoards.get(i).setLikeState(true);

                                                    }
                                                }

                                            }
                                        }


                                        Log.d(TAG, "onResponse: 동작함" + cmRespDto.getData().toString());
                                    }else{
                                        Log.d(TAG, "onResponse: 이거 널값인데");
                                        isLast = true;
                                    }

                                }

                                @Override
                                public void onFailure(Call<CMRespDto<List<Board>>> call, Throwable t) {

                                }
                            });

                            loadMore();
                            page = page + 10;
                        }
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {

        if (isLast == false){
            Log.d(TAG, "loadMore: isLast" + isLast);
            boards.add(null);
            communityAdapter.notifyItemInserted(boards.size() - 1);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    boards.remove(boards.size() - 1); //9 , 4개더 가져올 예정
                    int scrollPosition = boards.size(); //지금 스크롤 위치 = 10
                    communityAdapter.notifyItemRemoved(scrollPosition);
                    int currentSize = scrollPosition;
                    int nextLimit = currentSize + addBoards.size(); //10 + 4 = 14

                    //여기서 뿌려줘야함
//                for(int i = 0; i<boards.size(); i++){
//                    communityAdapter.addItem(CommunityFragment.this.addBoards.get(i));
//                }

                    int c = 0;
                    while (currentSize < nextLimit) { //9<14까지? => 10 , 11, 12, 13
                        boards.add(addBoards.get(c));
                        c++;
                        currentSize++;
                    }

                    communityAdapter.notifyDataSetChanged();

                    isLoading = false;
                }
            }, 2000);
        }else{
            isLoading = false;
            Toast.makeText(at, "마지막 게시글입니다", Toast.LENGTH_SHORT).show();
        }


    }

    public void init(View view){
        rvPostList = (RecyclerView) view.findViewById(R.id.rv_post_list);
        ftvPost = view.findViewById(R.id.ftv_post);

        ftv_search = view.findViewById(R.id.ftv_search);

        dl_community = (DrawerLayout) view.findViewById(R.id.dl_community);
        nv_community = (NavigationView) view.findViewById(R.id.nv_community);

        //버튼삼총사
        btn_favorite = view.findViewById(R.id.btn_favorite);
        btn_new = view.findViewById(R.id.btn_new);
        btn_top = view.findViewById(R.id.btn_top);
    }

    public void toolbarSetting(View view)
    {
        //toolBar를 통해 App Bar 생성
        Toolbar toolbar = view.findViewById(R.id.tb_community);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        //App Bar의 좌측 영영에 Drawer를 Open 하기 위한 Incon 추가
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                ((AppCompatActivity)getActivity()),
                dl_community,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );

        dl_community.addDrawerListener(actionBarDrawerToggle);
        nv_community.setNavigationItemSelectedListener(this::onNavigationItemSelected);


    }

    public void listener(MainActivity at){
        ftvPost.setOnClickListener(v -> {
            if(at.loginUser == null){
                Toast.makeText(at, "로그인이 필요한 서비스입니다", Toast.LENGTH_SHORT).show();
            } else{
                at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MakePostFragment()).commit();
            }

        });

        ftv_search.setOnClickListener(view -> {
            dl_community.openDrawer(GravityCompat.END);

            //헤더 뷰 접근
            View headerView = nv_community.getHeaderView(0);
            Button login = headerView.findViewById(R.id.btn_nav_login);
            if (at.loginUser == null){
                login.setText("로그인");
                login.setOnClickListener(v1 -> {
                    Log.d(TAG, "listen: 로그인 클릭됨");
                    dl_community.closeDrawer(GravityCompat.END);
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                });

            } else {
                login.setText("로그아웃");
                login.setOnClickListener(v1 -> {
                    MainActivity.loginUser = null;
                    dl_community.closeDrawer(GravityCompat.END);
                    Toast.makeText(at, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                });

            }
        });

        btn_favorite.setOnClickListener(v -> {
            //좋아요가 가장 많이 달린 글 순서
        });

        btn_new.setOnClickListener(v -> {
            //가장 최근에 달린 글 순서

        });

        btn_top.setOnClickListener(v -> {
            //좋아요 댓글 포함 젤 많은 글 순서
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_funny:
                Log.d(TAG, "onNavigationItemSelected: 클릭됨");
                Toast.makeText(at, "item1 clicked..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_free:
                Toast.makeText(at, "item2 clicked..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_setting_commu:
                Toast.makeText(at, "item3 clicked..", Toast.LENGTH_SHORT).show();
                break;
        }

        dl_community.closeDrawer(GravityCompat.END);
        return false;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.community_menu:{ // 왼쪽 상단 버튼 눌렀을 때
                Log.d(TAG, "onOptionsItemSelected: 선택됨");
                //로직짜야함
                dl_community.openDrawer(GravityCompat.END);

                //로그인 Intent
//            if (at.loginUser == null){
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
//            } else {
//                Toast.makeText(at, "현재 로그인중인 사람은 : " + at.loginUser.getUsername(), Toast.LENGTH_SHORT).show();
//            }


                //헤더 뷰 접근
                View headerView = nv_community.getHeaderView(0);
                Button login = headerView.findViewById(R.id.btn_nav_login);
                if (at.loginUser == null){
                    login.setText("로그인");
                    login.setOnClickListener(v1 -> {
                        Log.d(TAG, "listen: 로그인 클릭됨");
                        dl_community.closeDrawer(GravityCompat.END);
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    });

                } else {
                    login.setText("로그아웃");
                    login.setOnClickListener(v1 -> {
                        MainActivity.loginUser = null;
                        dl_community.closeDrawer(GravityCompat.END);
                        Toast.makeText(at, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                    });

                }

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.community_toolbar_menu, menu);
    }

}