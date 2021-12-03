package com.enfotrix.smartfarmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.enfotrix.smartfarmer.classes.Utils;
import com.lusfold.spinnerloading.SpinnerLoading;

public class SplashActivity extends AppCompatActivity {
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        utils = new Utils(this);
//        SpinnerLoading spinner_loading = findViewById(R.id.spinner_loading);
//        spinner_loading.setPaintMode(1);
//        spinner_loading.setCircleRadius(10);
//        spinner_loading.setItemCount(8);
        delay();
    }

    private void delay() {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (utils.isLoggedIn()) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 4000);
    }
}