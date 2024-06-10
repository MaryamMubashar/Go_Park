package com.example.gopark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.example.gopark.data.User;
import com.example.gopark.room.AppDatabase;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int SPLASH_DISPLAY_LENGTH = 1000;

//        if (user!=null){
//            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//            finish();
//        }

        new Handler(getMainLooper()).postDelayed(() -> {

                // Check if user is logged in
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

                if (isLoggedIn) {
                    // User is logged in, navigate to HomeActivity
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                } else {
                    // User is not logged in, navigate to LoginActivity
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();

        }, SPLASH_DISPLAY_LENGTH);
    }
}