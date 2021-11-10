package com.bhavesh.surveyapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bhavesh.surveyapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity  extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.splash_activity);
sentIntent();
    }

    private void sentIntent() {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub

                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 1000);


    }
}
