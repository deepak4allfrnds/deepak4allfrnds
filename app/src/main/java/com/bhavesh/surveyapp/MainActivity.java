package com.bhavesh.surveyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnx.surveyapp.R;
import com.google.android.material.navigation.NavigationView;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public NavigationView navigationView;
    public DrawerLayout mdrawerLayout;
    private ImageView iv_user_image;
    private TextView tv_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setupDrawer();
    }

    private void setupDrawer() {
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        iv_user_image = headerView.findViewById(R.id.iv__nav_player_image);
        tv_user_name = headerView.findViewById(R.id.tv__nav_player_name);
        mdrawerLayout = findViewById(R.id.drawer_layout);
    }


}