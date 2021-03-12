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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.cos.javagg.MainActivity;
import com.cos.javagg.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class SearchFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener
{
    //private DrawerLayout mDrawerLayout;
    private DrawerLayout drawLayout;
    private NavigationView navigationView;
    private ImageButton draw;
    private Vibrator vibrator;
    private ImageButton btn1;
    private Context at;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        at = (MainActivity)container.getContext();

        //mDrawerLayout=view.findViewById(R.id.drawerLayout);
        //draw = view.findViewById(R.id.draw);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        btn1=view.findViewById(R.id.img_button);

        InitializeLayout(view);
        //drawL();    // 드로우레이아웃
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

//    public void drawL(){
//        draw.setOnClickListener(v -> {
//            mDrawerLayout.openDrawer(GravityCompat.START);
//        });
//    }

    public void InitializeLayout(View view)
    {
        //toolBar를 통해 App Bar 생성
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        //App Bar의 좌측 영영에 Drawer를 Open 하기 위한 Incon 추가
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_glyph_solid_gamepad);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        drawLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) view.findViewById(R.id.nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                ((AppCompatActivity)getActivity()),
                drawLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );

        drawLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);


    }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()) {
                case R.id.account:
                    Toast.makeText(at, "item1 clicked..", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.setting:
                    Toast.makeText(at, "item2 clicked..", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.logout:
                    Toast.makeText(at, "item3 clicked..", Toast.LENGTH_SHORT).show();
                    break;
            }

            drawLayout.closeDrawer(GravityCompat.START);
            return false;

        }


}