package com.cos.javagg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class JoinActivity extends AppCompatActivity {
    private static final String TAG = "JoinActivity";


    private ImageButton join_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        findById();


        listener();



    }

    public void findById(){
        join_close = findViewById(R.id.close);

    }

    public void listener(){
        join_close.setOnClickListener(v -> {
            this.finish();
        });

    }
}