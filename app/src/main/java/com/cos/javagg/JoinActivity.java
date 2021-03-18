package com.cos.javagg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class JoinActivity extends AppCompatActivity {

    private ImageButton join_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        join_close = findViewById(R.id.close);

        join_close.setOnClickListener(v -> {
            this.finish();
        });

    }
}