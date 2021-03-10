package com.cos.javagg.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.cos.javagg.MainActivity;
import com.cos.javagg.R;
import com.google.android.material.navigation.NavigationView;

public class SearchFragment extends Fragment {
    private Toolbar toolbar;
    private Vibrator vibrator;
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private Button btn1;
    private MainActivity at;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        at = (MainActivity)container.getContext();
        View view = inflater.inflate(R.layout.fragment_search,container,false);


//       툴바 셋팅
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        MainActivity activity = (MainActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);          // 바_게임
        actionBar.setHomeAsUpIndicator(R.mipmap.game);

        View viewToolbar = getActivity().getLayoutInflater().inflate(R.layout.toolbar_search, null);
        setHasOptionsMenu(true);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) { // res/menu 에서 친구 탭에서 작동 할 menu를 가져온다.
        inflater.inflate(R.menu.searchnavi_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}