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
import com.cos.javagg.dto.CMRespDto;
import com.cos.javagg.dto.BoardDto;
import com.cos.javagg.service.CommunityApi;

import in.nashapp.androidsummernote.Summernote;
import info.androidhive.fontawesome.FontTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakePostFragment extends Fragment {

    private static final String TAG = "MakePostFragment";
    private FontTextView ftvAddBack;
    private Summernote summernote;
    private AppCompatEditText et_title;
    private CommunityApi communityApi;
    private Call<CMRespDto<String>> call;
    private Button btn_postsave;
    private BoardDto postDto;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity at = (MainActivity)container.getContext();

        View view = inflater.inflate(R.layout.fragment_makepost,container,false);

        findByID(view);


        스피너(view); //글 종류(일반, 유머 등)

        //썸머노트
        summernote.setRequestCodeforFilepicker(5);//Any Number which is not being used by other OnResultActivity

        listener(at);

        return view;
    }

    private void listener(MainActivity at) {
        ftvAddBack.setOnClickListener(v -> {
            at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CommunityFragment()).commit();
        });

        btn_postsave.setOnClickListener(v -> {
            if(et_title.getText().toString().length() != 0 && summernote.getText().toString().length() != 0){
                postDto.setTitle(et_title.getText()+"");
                postDto.setContent(summernote.getText());

                call = communityApi.save(at.loginUser.getId(),postDto);

                call.enqueue(new Callback<CMRespDto<String>>() {
                    @Override
                    public void onResponse(Call<CMRespDto<String>> call, Response<CMRespDto<String>> response) {
                        CMRespDto<String> cmRespDto = response.body();
                        if(cmRespDto.getResultCode() == 1){
                            at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CommunityFragment()).commit();
                            Toast.makeText(at, "게시물 저장 완료", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CMRespDto<String>> call, Throwable t) {

                    }
                });
            }else{
                Toast.makeText(at, "제목과 내용을 적었는지 확인하세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findByID(View view) {
        et_title = view.findViewById(R.id.et_title);
        btn_postsave = view.findViewById(R.id.btn_postsave);
        ftvAddBack = view.findViewById(R.id.ftv_addback);
        summernote = (Summernote) view.findViewById(R.id.summernote);
        postDto = new BoardDto();
        communityApi = CommunityApi.retrofit.create(CommunityApi.class);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        summernote.onActivityResult(requestCode, resultCode, intent);
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
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

}
