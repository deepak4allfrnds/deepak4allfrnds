package com.bhavesh.surveyapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.cnx.surveyapp.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.splash_activity);
        ButterKnife.bind(this);
        sentIntent();

    }

    public void sentIntent() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

//                if (SessionManager.get_check_login(prefs)) {
//                    //  if(SessionManager.get_otp_verification(prefs).equalsIgnoreCase("OTP verified successfully.")) {
//                    if (SessionManager.get_userType(prefs).equals("Customer")) {
//                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                    /*}else {
//                        Intent intent = new Intent(SplashScreen.this, GetStartedActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }*/
//                } else {
//                    Intent intent = new Intent(SplashScreen.this, GetStartedActivity.class);
//                    startActivity(intent);
//                    finish();
//
//                }

            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);


    }
}
