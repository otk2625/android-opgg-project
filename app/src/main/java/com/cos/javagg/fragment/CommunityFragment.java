package com.cos.javagg.fragment;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.List;

import info.androidhive.fontawesome.FontTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, AbsListView.OnScrollListener {
    private RecyclerView rvPostList;
    private RecyclerView.LayoutManager postLayoutManager;
    private DrawerLayout dl_community;
    private NavigationView nv_community;
    private CommunityAdapter communityAdapter;
    private FontTextView ftvPost, ftv_login;
    private static final String TAG = "CommunityFragment";
    private Button btn_favorite, btn_new, btn_top;
    private MainActivity at;
    private CommunityApi communityApi;
    private Call<CMRespDto<List<Board>>> call;
    private List<Board> boards;

    //페이징
    private boolean lastItemVisibleFlag = false;
    private int page = 0;                           // 페이징변수. 초기 값은 0 이다.
    private final int OFFSET = 20;                  // 한 페이지마다 로드할 데이터 갯수.
    private ProgressBar progressBar;                // 데이터 로딩중을 표시할 프로그레스바
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수

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
        call = communityApi.findAll();

        call.enqueue(new Callback<CMRespDto<List<Board>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<Board>>> call, Response<CMRespDto<List<Board>>> response) {
                CMRespDto<List<Board>> cmRespDto = response.body();
                boards = cmRespDto.getData();

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


        return view;
    }

    public void init(View view){
        rvPostList = (RecyclerView) view.findViewById(R.id.rv_post_list);
        ftvPost = view.findViewById(R.id.ftv_post);

        ftv_login = view.findViewById(R.id.ftv_login);

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

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        // 1. OnScrollListener.SCROLL_STATE_IDLE : 스크롤이 이동하지 않을때의 이벤트(즉 스크롤이 멈추었을때).
        // 2. lastItemVisibleFlag : 리스트뷰의 마지막 셀의 끝에 스크롤이 이동했을때.
        // 3. mLockListView == false : 데이터 리스트에 다음 데이터를 불러오는 작업이 끝났을때.
        // 1, 2, 3 모두가 true일때 다음 데이터를 불러온다.
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false) {
            // 화면이 바닦에 닿을때 처리
            // 로딩중을 알리는 프로그레스바를 보인다.
            progressBar.setVisibility(View.VISIBLE);

            // 다음 데이터를 불러온다.
            //getItem();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // firstVisibleItem : 화면에 보이는 첫번째 리스트의 아이템 번호.
        // visibleItemCount : 화면에 보이는 리스트 아이템의 갯수
        // totalItemCount : 리스트 전체의 총 갯수
        // 리스트의 갯수가 0개 이상이고, 화면에 보이는 맨 하단까지의 아이템 갯수가 총 갯수보다 크거나 같을때.. 즉 리스트의 끝일때. true
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
    }

//    private void getItem(){
//
//        // 리스트에 다음 데이터를 입력할 동안에 이 메소드가 또 호출되지 않도록 mLockListView 를 true로 설정한다.
//        mLockListView = true;
//
//        // 다음 20개의 데이터를 불러와서 리스트에 저장한다.
//        for(int i = 0; i < 20; i++){
//            String label = "Label " + ((page * OFFSET) + i);
//            boards.add(label);
//        }
//
//        // 1초 뒤 프로그레스바를 감추고 데이터를 갱신하고, 중복 로딩 체크하는 Lock을 했던 mLockListView변수를 풀어준다.
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                page++;
//                adapter.notifyDataSetChanged();
//                progressBar.setVisibility(View.GONE);
//                mLockListView = false;
//            }
//        },1000);
//    }
}