package com.cos.javagg.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.fontawesome.FontTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPostFragment  extends Fragment {
    private static final String TAG = "DetailPostFragment";
    private RecyclerView rvReplyList;
    private RecyclerView.LayoutManager replyLayoutManager;
    private ReplyAdapter replyAdapter;
    private FontTextView ftvAddBack;
    private Button btn_delete, btn_update, btn_replysave;
    private  MainActivity at;
    private TextView tv_detail_title, tv_detail_createdate, tv_detail_username, tv_detail_content, tv_detail_postkind, tv_readcount
            ,tv_reply_total_count;
    private HtmlTextView htmlTextView; //내용임
    private EditText et_replycontent;
    private CommunityApi communityApi;
    private Call<CMRespDto<String>> call;
    private Board board;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        at = (MainActivity)container.getContext();

        View view = inflater.inflate(R.layout.fragment_detailpost,container,false);

        findById(view);

        //사용자 있는지 체크
        if(at.loginUser != null){
            btn_delete.setVisibility(View.VISIBLE);
            btn_update.setVisibility(View.VISIBLE);
        }

        communityApi = CommunityApi.retrofit.create(CommunityApi.class);


        //데이터 뿌리기
        dataset();




         //댓글 어댑터 처리
        if (board.getReplys().isEmpty() == true){
            replyAdapter = new ReplyAdapter(null);
        }else{
            List<Reply> replys = board.getReplys();
            replyAdapter = new ReplyAdapter(replys);
        }

        replyLayoutManager = new LinearLayoutManager(getActivity());
        rvReplyList.setLayoutManager(replyLayoutManager);
        rvReplyList.setAdapter(replyAdapter);





        listener(view);

        return view;
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


        if(board.getReplys().isEmpty() != true){
            tv_reply_total_count.setText(board.getReplys().size()+"");
        }


    }

    public void findById(View view){
        //어댑터
        rvReplyList = (RecyclerView) view.findViewById(R.id.rv_reply_list);


        et_replycontent = view.findViewById(R.id.et_replycontent);
        btn_replysave = view.findViewById(R.id.btn_replysave);
        btn_delete = view.findViewById(R.id.btn_delete);
        btn_update = view.findViewById(R.id.btn_update);


        ftvAddBack = view.findViewById(R.id.ftv_DetailPostback);

        tv_detail_title = view.findViewById(R.id.tv_detail_title);
        tv_detail_createdate = view.findViewById(R.id.tv_reply_username);
        tv_detail_username = view.findViewById(R.id.tv_reply_createdate);

        tv_detail_postkind = view.findViewById(R.id.tv_detail_postkind);
        tv_readcount = view.findViewById(R.id.tv_readcount);

        htmlTextView = (HtmlTextView) view.findViewById(R.id.html_text);

        //댓글 총 개수
        tv_reply_total_count = view.findViewById(R.id.tv_reply_total_count);

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
                    call.enqueue(new Callback<CMRespDto<String>>() {
                        @Override
                        public void onResponse(Call<CMRespDto<String>> call, Response<CMRespDto<String>> response) {
                            //댓글 성공시

                            CMRespDto<String> cmRespDto = response.body();

                                //새로고침
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.detach(DetailPostFragment.this).attach(DetailPostFragment.this).commit();
                                Toast.makeText(at, "댓글쓰기 완료", Toast.LENGTH_SHORT).show();





                        }

                        @Override
                        public void onFailure(Call<CMRespDto<String>> call, Throwable t) {

                        }
                    });

                } else{
                    Toast.makeText(at, "댓글을 작성해 주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //뒤로가기 버튼
        ftvAddBack.setOnClickListener(v -> {
            at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CommunityFragment()).commit();
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

                    call = communityApi.delete(board.getId());
                    call.enqueue(new Callback<CMRespDto<String>>() {
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
    }
}
