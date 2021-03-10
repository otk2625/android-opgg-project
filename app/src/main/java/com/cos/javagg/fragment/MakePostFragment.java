package com.cos.javagg.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.cos.javagg.MainActivity;
import com.cos.javagg.R;

import in.nashapp.androidsummernote.Summernote;
import info.androidhive.fontawesome.FontTextView;

public class MakePostFragment extends Fragment {

    private static final String TAG = "MakePostFragment";
    private FontTextView ftvAddBack;
    private Summernote summernote;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity at = (MainActivity)container.getContext();

        View view = inflater.inflate(R.layout.fragment_makepost,container,false);

        ftvAddBack = view.findViewById(R.id.ftv_addback);
        ftvAddBack.setOnClickListener(v -> {
            at.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CommunityFragment()).commit();
        });

        스피너(view);

        summernote = (Summernote) view.findViewById(R.id.summernote);
        summernote.setRequestCodeforFilepicker(5);//Any Number which is not being used by other OnResultActivity

        return view;
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
