package com.cos.javagg.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.cos.javagg.MainActivity;
import com.cos.javagg.R;
import com.cos.javagg.dto.BoardDto;
import com.cos.javagg.dto.BoardUpdateDto;
import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.model.board.Board;
import com.cos.javagg.service.CommunityApi;

import in.nashapp.androidsummernote.Summernote;
import info.androidhive.fontawesome.FontTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class updatePostFragment extends Fragment {

    private static final String TAG = "MakePostFragment";
    private FontTextView ftvAddBack;
    private Summernote update_summernote;
    private AppCompatEditText et_update_title;
    private CommunityApi communityApi;
    private Call<CMRespDto<Board>> call;
    private Button btn_postupdate;
    private BoardUpdateDto boardUpdateDto;
    private  MainActivity at;
    public String postKinds = ""; //자유, 유머, 영상, 팁과 노하우
    private Board board;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        at = (MainActivity)container.getContext();

        View view = inflater.inflate(R.layout.fragment_updatepost,container,false);

        board = MainActivity.board;

        findByID(view);

        //세팅
        et_update_title.setText(board.getTitle());
        update_summernote.setText(board.getContent());

        //썸머노트
        update_summernote.setRequestCodeforFilepicker(5);//Any Number which is not being used by other OnResultActivity

        communityApi = CommunityApi.retrofit.create(CommunityApi.class);

        listener(at);

        return view;
    }

    private void listener(MainActivity at) {
        ftvAddBack.setOnClickListener(v -> {
            at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DetailPostFragment()).commit();
        });

        //수정 버튼
        btn_postupdate.setOnClickListener(v -> {
            if(et_update_title.getText().toString().length() != 0 && update_summernote.getText().toString().length() != 0) {
                //수정 로직

                boardUpdateDto = new BoardUpdateDto();
                boardUpdateDto.setTitle(et_update_title.getText()+"");
                boardUpdateDto.setContent(update_summernote.getText()+"");

                call = communityApi.update(board.getId(), boardUpdateDto);

                call.enqueue(new Callback<CMRespDto<Board>>() {
                    @Override
                    public void onResponse(Call<CMRespDto<Board>> call, Response<CMRespDto<Board>> response) {
                        CMRespDto<Board> cmRespDto = response.body();

                        Log.d(TAG, "onResponse: 수정시 : " + cmRespDto.getResultCode());

                        if (cmRespDto.getResultCode() == 1){
                            Toast.makeText(at, "수정이 완료되었습니다", Toast.LENGTH_SHORT).show();
                            MainActivity.board = cmRespDto.getData();
                            at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DetailPostFragment()).commit();
                        }else{
                            Toast.makeText(at, "수정실패", Toast.LENGTH_SHORT).show();
                        }

                        at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DetailPostFragment()).commit();
                    }

                    @Override
                    public void onFailure(Call<CMRespDto<Board>> call, Throwable t) {

                    }
                });


            }else{
                Toast.makeText(at, "제목과 내용이 비었는지 확인하세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findByID(View view) {
        btn_postupdate = view.findViewById(R.id.btn_postupdate);
        ftvAddBack = view.findViewById(R.id.ftv_addback);
        update_summernote = (Summernote) view.findViewById(R.id.update_summernote);
        et_update_title = view.findViewById(R.id.et_update_title);
        communityApi = CommunityApi.retrofit.create(CommunityApi.class);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        update_summernote.onActivityResult(requestCode, resultCode, intent);
    }

    public void 스피너(View view){
        final Spinner spinner_field = (Spinner) view.findViewById(R.id.spinner_field);
        String[] str = getResources().getStringArray(R.array.spinnerArray);

        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(getContext(),R.layout.spinner_item,str);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_field.setAdapter(adapter);

        spinner_field.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinner_field.getSelectedItemPosition() > 0){
                    //선택된 항목
                    Log.v("알림",spinner_field.getSelectedItem().toString()+ "is selected");

                    postKinds = spinner_field.getSelectedItem().toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

}
