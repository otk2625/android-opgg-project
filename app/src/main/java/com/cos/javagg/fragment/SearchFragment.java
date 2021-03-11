package com.cos.javagg.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.cos.javagg.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class SearchFragment extends Fragment {

    private DrawerLayout mDrawerLayout;
    private ImageButton draw;
    private Vibrator vibrator;
    private ImageButton btn1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        mDrawerLayout=view.findViewById(R.id.drawerLayout);
        draw = view.findViewById(R.id.draw);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        btn1=view.findViewById(R.id.img_button);

        drawL();    // 드로우레이아웃
        lolclick(); // 롤 이미지클릭

        return view;
    }

    public void lolclick() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
            }
        });
    }

    public void drawL(){
        draw.setOnClickListener(v -> {
            mDrawerLayout.openDrawer(GravityCompat.START);
        });
    }
}