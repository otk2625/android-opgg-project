package com.cos.javagg.toolbar;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cos.javagg.R;
import com.google.android.material.navigation.NavigationView;


public class searchToolbar extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private Context context = this;
    private NavigationView navigationView;
    private ImageButton btn1;
    private Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_search);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);        // 제목없애기
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);          // 바_게임
        actionBar.setHomeAsUpIndicator(R.mipmap.game);      // 바_게임호출
        mDrawerLayout =findViewById(R.id.drawerLayout);     // 드로우레이아웃
        navigationView =findViewById(R.id.nav_view);


        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); // 진동
        btn1 = findViewById(R.id.img_button);    // 롤이미지

        lolclick();     // 롤이미지 클릭

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_community, menu);
        return true;
    }

    public void lolclick(){     // 롤이미지 클릭
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.drawerLayout:
                Toast.makeText(getApplicationContext(), "환경설정 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return true;

            default:
                Toast.makeText(getApplicationContext(), "미구현", Toast.LENGTH_LONG).show();
                mDrawerLayout.openDrawer(GravityCompat.START);
                return super.onOptionsItemSelected(item);

        }

    }


}