package com.cos.javagg.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.cos.javagg.MainActivity;
import com.cos.javagg.R;
import com.cos.javagg.SearchResultActivity;
import com.google.android.material.navigation.NavigationView;

public class SearchFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener
{
    private static final String TAG = "SearchFragment";
    //private DrawerLayout mDrawerLayout;
    private DrawerLayout drawLayout;
    private NavigationView navigationView;
    private ImageButton ibSearchButton, btn1;
    private Vibrator vibrator;
    private Context at;
    private EditText etSearchName;
    private boolean enterKeyDown;
    private boolean enterKeyUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        at = (MainActivity)container.getContext();

        if (MainActivity.noSummoner == true){
            Toast toast = Toast.makeText(at, "없는 소환사 이름입니다", Toast.LENGTH_LONG);
            MainActivity.noSummoner = false;
        }

        //mDrawerLayout=view.findViewById(R.id.drawerLayout);
        //draw = view.findViewById(R.id.draw);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        btn1=view.findViewById(R.id.img_button);
        etSearchName = view.findViewById(R.id.et_search_input);
        ibSearchButton = view.findViewById(R.id.ib_search_button);

        InitializeLayout(view);
        //drawL();    // 드로우레이아웃
        lolclick(); // 롤 이미지클릭


        // 엔터로 검색
//        etSearchName.setOnKeyListener((v, keyCode, event) -> {
//                if (keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP && !enterKeyUp) {
//                    enterKeyUp = true;
//                    // 액티비티 이동
//                    moveToNext();
//                    return true;
//                } else if (keyCode == event.KEYCODE_ENTER) {
//                    enterKeyDown = true;
//                    return true;
//                }
//                return false;
//        });


        // 터치로 검색
        ibSearchButton.setOnClickListener(v -> {
            enterKeyUp = false;
            moveToNext();
        });


        return view;
    }

    private void moveToNext() {
        //여기서 막아야함
        if (etSearchName.getText().toString() == null || etSearchName.getText().toString().equals("")) {
//            Toast.makeText(getContext(), "소환사 이름을 입력하세요", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "moveToNext: 실행됨");
            Toast toast = Toast.makeText(at, "소환사 이름을 입력하세요", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Intent intent = new Intent(getActivity(), SearchResultActivity.class);
        intent.putExtra("summonerName", etSearchName.getText().toString());
        Log.d(TAG, "moveToNext: 소환사 이름 : " + etSearchName.getText().toString());
//        // 이전화면을 없애고 새화면을 띄운다
//        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        etSearchName.setText("");
        startActivity(intent);
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