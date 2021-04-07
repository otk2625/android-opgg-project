package com.cos.javagg.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.javagg.LoginActivity;
import com.cos.javagg.MainActivity;
import com.cos.javagg.R;
import com.cos.javagg.adapter.CommunityAdapter;
import com.cos.javagg.adapter.ReplyAdapter;
import com.cos.javagg.champ.Calcu;
import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.ReplyDto;
import com.cos.javagg.model.board.Board;
import com.cos.javagg.model.reply.Reply;
import com.cos.javagg.service.CommunityApi;
import com.google.android.material.navigation.NavigationView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.fontawesome.FontTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPostFragment  extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "DetailPostFragment";
    private RecyclerView rvReplyList;
    private RecyclerView.LayoutManager replyLayoutManager;
    private ReplyAdapter replyAdapter;
    private FontTextView ftvAddBack;
    private Button btn_delete, btn_update, btn_replysave;
    private  MainActivity at;
    private TextView tv_detail_title, tv_detail_createdate, tv_detail_username, tv_detail_content, tv_detail_postkind, tv_readcount
            ,tv_reply_total_count, tv_reply_count, tv_likescount;
    private HtmlTextView htmlTextView; //내용임
    private EditText et_replycontent;
    private CommunityApi communityApi;
    private Call<CMRespDto<Reply>> call;
    private Call<CMRespDto<String>> call2;
    private Call<CMRespDto<Integer>> call3;
    private Call<CMRespDto<Integer>> call4;
    private Board board;
    private DrawerLayout dl_community_detail;
    private NavigationView nv_community_detail;
    private AppCompatButton btn_unLike, btn_like;
    private int likeId = 0;
    private boolean toogleBtn = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        at = (MainActivity)container.getContext();
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_detailpost,container,false);

        findById(view);

        //툴바 세팅
        toolBarSetting(view);

        //데이터 뿌리기
        dataset();


         //댓글 어댑터 처리
        if (board.getReplys().isEmpty() == true){

        }else{
            List<Reply> replys = board.getReplys();
            replyAdapter = new ReplyAdapter(replys);
            replyLayoutManager = new LinearLayoutManager(getActivity());
            rvReplyList.setLayoutManager(replyLayoutManager);
            rvReplyList.setAdapter(replyAdapter);
        }

        //사용자 있는지 체크
        if(at.loginUser == null){

        }else{
            if(at.loginUser.getId() == board.getUser().getId() ){
                btn_delete.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
            }
        }



        listener(view);

        return view;
    }

    private void toolBarSetting(View view) {
        //toolBar를 통해 App Bar 생성
        Toolbar toolbar = view.findViewById(R.id.tb_community_detail);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //App Bar의 좌측 영영에 Drawer를 Open 하기 위한 Incon 추가
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        nv_community_detail.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        communityApi = CommunityApi.retrofit.create(CommunityApi.class);
    }

    private void dataset() {
        board = MainActivity.board;

        Log.d(TAG, "dataset: " + board.toString());

        tv_detail_title.setText(board.getTitle());
        tv_detail_username.setText(board.getUser().getUsername());

        tv_detail_postkind.setText(board.getCommunityType());
        tv_detail_createdate.setText(Calcu.getDate(board.getCreateDate()));

        htmlTextView.setHtml(board.getContent());

        tv_readcount.setText(board.getReadCount()+"");

        tv_reply_count.setText(board.getReplys().size()+"");

        if(board.getReplys().isEmpty() != true){
            tv_reply_total_count.setText(board.getReplys().size()+"");
        }


        tv_likescount.setText(board.getLikes().size()+"");

        btn_like.setText(board.getLikes().size()+"");
        //좋아요 버튼 관리
        if(MainActivity.loginUser != null){
            if(board.isLikeState() == true){
                btn_like.setBackgroundColor(Color.parseColor("#30DAA4"));
                btn_like.setText(board.getLikeCount()+"");
                toogleBtn = true;
            }else{
                //btn_like.setBackgroundColor(Color.parseColor("#C5CBD0"));
                btn_like.setBackgroundColor(Color.parseColor("#FFFFFF"));
                toogleBtn = false;
            }
        }else{
            btn_like.setBackgroundColor(Color.parseColor("#FFFFFF"));
            toogleBtn = false;
        }


        //btn_like.setText(board.getLikeCount()+"");

    }

    public void findById(View view){
        //어댑터
        rvReplyList = (RecyclerView) view.findViewById(R.id.rv_reply_list);

        //좋아요 싫어요 버튼
        //btn_unLike = view.findViewById(R.id.btn_unLike);
        btn_like = view.findViewById(R.id.btn_like);

        et_replycontent = view.findViewById(R.id.et_replycontent);
        btn_replysave = view.findViewById(R.id.btn_replysave);
        btn_delete = view.findViewById(R.id.btn_delete);
        btn_update = view.findViewById(R.id.btn_update);


        //ftvAddBack = view.findViewById(R.id.ftv_DetailPostback);

        tv_detail_title = view.findViewById(R.id.tv_detail_title);
        tv_detail_createdate = view.findViewById(R.id.tv_reply_username);
        tv_detail_username = view.findViewById(R.id.tv_reply_createdate);

        tv_detail_postkind = view.findViewById(R.id.tv_detail_postkind);
        tv_readcount = view.findViewById(R.id.tv_readcount);

        htmlTextView = (HtmlTextView) view.findViewById(R.id.html_text);

        //댓글 총 개수
        tv_reply_total_count = view.findViewById(R.id.tv_reply_total_count);
        tv_reply_count = view.findViewById(R.id.tv_reply_count);

        dl_community_detail = (DrawerLayout) view.findViewById(R.id.dl_community_detail);
        nv_community_detail = (NavigationView) view.findViewById(R.id.nv_community_detail);

        //좋아요 개수
        tv_likescount = view.findViewById(R.id.tv_likescount);
    }

    public void listener(View view){
        //댓글 쓰기 버튼
        btn_replysave.setOnClickListener(v -> {
            if(MainActivity.loginUser == null){
                Toast.makeText(at, "로그인이 필요한 서비스 입니다", Toast.LENGTH_SHORT).show();
            } else {
                if (et_replycontent.getText().toString().length() != 0 ) {
                    //여기서 댓글 저장
                    ReplyDto replyDto = ReplyDto.builder()
                            .boardId(board.getId())
                            .userId(MainActivity.loginUser.getId())
                            .content(et_replycontent.getText() + "")
                            .build();

                    call = communityApi.reply(replyDto);
                    call.enqueue(new Callback<CMRespDto<Reply>>() {
                        @Override
                        public void onResponse(Call<CMRespDto<Reply>> call, Response<CMRespDto<Reply>> response) {
                            //댓글 성공시

                            CMRespDto<Reply> cmRespDto = response.body();

                            replyAdapter.addItem(cmRespDto.getData());
                            Log.d(TAG, "onResponse: 댓글" + cmRespDto.getData().toString());

                            et_replycontent.setText("");
                            Toast.makeText(at, "댓글쓰기 완료", Toast.LENGTH_SHORT).show();

                            int count1 = Integer.parseInt(tv_reply_total_count.getText()+"");
                            tv_reply_total_count.setText(count1++ + "");

                            int count2 = Integer.parseInt(tv_reply_count.getText()+"");
                            tv_reply_count.setText(count2++ + "");
                        }

                        @Override
                        public void onFailure(Call<CMRespDto<Reply>> call, Throwable t) {

                        }
                    });

                } else{
                    Toast.makeText(at, "댓글을 작성해 주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //삭제 버튼
        btn_delete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(at);

            builder.setTitle("").setMessage("삭제 하시겠습니까?");

            builder.setPositiveButton("삭제", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    //삭제 로직

                    call2 = communityApi.delete(board.getId());
                    call2.enqueue(new Callback<CMRespDto<String>>() {
                        @Override
                        public void onResponse(Call<CMRespDto<String>> call, Response<CMRespDto<String>> response) {
                            CMRespDto<String> cmRespDto = response.body();

                            if(cmRespDto.getResultCode() == 1){
                                Toast.makeText(at, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                board = null;
                                at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CommunityFragment()).commit();
                            }

                        }

                        @Override
                        public void onFailure(Call<CMRespDto<String>> call, Throwable t) {

                        }
                    });
                }
            });

            builder.setNegativeButton("닫기",null);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        });

        //수정클릭
        btn_update.setOnClickListener(v -> {
            at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new updatePostFragment()).commit();
        });

        //좋아요 버튼
        btn_like.setOnClickListener(view1 -> {
            if(MainActivity.loginUser != null){
                if(board.isLikeState()){
                    //지금 좋아요 되어있다는뜻 눌렀을때 삭제 되어야함
                    call4 = communityApi.likesId(board.getId(), MainActivity.loginUser.getId());
                    call4.enqueue(new Callback<CMRespDto<Integer>>() {
                        @Override
                        public void onResponse(Call<CMRespDto<Integer>> call, Response<CMRespDto<Integer>> response) {
                            CMRespDto<Integer> cmRespDto = response.body();

                            Log.d(TAG, "onResponse: 좋아요 id" + cmRespDto.getData());

                            likeId = cmRespDto.getData();
                            call2 = communityApi.unlikes(likeId);
                            call2.enqueue(new Callback<CMRespDto<String>>() {
                                @Override
                                public void onResponse(Call<CMRespDto<String>> call, Response<CMRespDto<String>> response) {
                                    int lkes = Integer.parseInt(btn_like.getText().toString());
                                    lkes--;
                                    btn_like.setText(lkes+"");

                                    board.setLikeState(false);
                                    btn_like.setBackgroundColor(Color.parseColor("#C5CBD0"));

                                    int count = Integer.parseInt(tv_likescount.getText()+"");
                                    count--;
                                    tv_likescount.setText(count+"");

                                    toogleBtn = false;
                                    likeId = 0;
                                }

                                @Override
                                public void onFailure(Call<CMRespDto<String>> call, Throwable t) {

                                }
                            });

                        }

                        @Override
                        public void onFailure(Call<CMRespDto<Integer>> call, Throwable t) {

                        }
                    });


                }else{
                    call3 = communityApi.likes(board.getId(), MainActivity.loginUser.getId());
                    call3.enqueue(new Callback<CMRespDto<Integer>>() {
                        @Override
                        public void onResponse(Call<CMRespDto<Integer>> call, Response<CMRespDto<Integer>> response) {
                            CMRespDto<Integer> cmRespDto = response.body();

                            int likes = Integer.parseInt(btn_like.getText()+"");
                            Log.d(TAG, "onResponse: likes숫자" + likes+1);
                            likes++;
                            btn_like.setText(likes+"");

                            likeId = cmRespDto.getData();

                            int count = Integer.parseInt(tv_likescount.getText()+"");
                            count++;
                            tv_likescount.setText(count+"");

                            board.setLikeState(true);
                            btn_like.setBackgroundColor(Color.parseColor("#30DAA4"));

                        }

                        @Override
                        public void onFailure(Call<CMRespDto<Integer>> call, Throwable t) {

                        }
                    });
                }

            }else{
                Toast.makeText(at, "로그인이 필요한 서비스입니다", Toast.LENGTH_SHORT).show();
            }


        });

        //싫어요 버튼
//        btn_unLike.setOnClickListener(view1 -> {
//            if(MainActivity.loginUser != null){
//
//
//                Log.d(TAG, "listener: 아니 좋아요 아이디 먼데 : " + likeId);
//
//
//            }else{
//                Toast.makeText(at, "로그인이 필요한 서비스입니다", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                //뒤로가기
                at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CommunityFragment()).commit();
                return true;
            }
            case R.id.community_menu:{ // 왼쪽 상단 버튼 눌렀을 때
                Log.d(TAG, "onOptionsItemSelected: 선택됨");
                //로직짜야함
                dl_community_detail.openDrawer(GravityCompat.END);

                //로그인 Intent
//            if (at.loginUser == null){
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
//            } else {
//                Toast.makeText(at, "현재 로그인중인 사람은 : " + at.loginUser.getUsername(), Toast.LENGTH_SHORT).show();
//            }


                //헤더 뷰 접근
                View headerView = nv_community_detail.getHeaderView(0);
                Button login = headerView.findViewById(R.id.btn_nav_login);
                if (at.loginUser == null){
                    login.setText("로그인");
                    login.setOnClickListener(v1 -> {
                        Log.d(TAG, "listen: 로그인 클릭됨");
                        dl_community_detail.closeDrawer(GravityCompat.END);
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    });

                } else {
                    login.setText("로그아웃");
                    login.setOnClickListener(v1 -> {
                        MainActivity.loginUser = null;
                        dl_community_detail.closeDrawer(GravityCompat.END);
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

        dl_community_detail.closeDrawer(GravityCompat.END);
        return false;

    }
}
