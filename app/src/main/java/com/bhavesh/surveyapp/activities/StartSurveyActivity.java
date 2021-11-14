package com.bhavesh.surveyapp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhavesh.surveyapp.adapter.StartSurveyAdapter;
import com.bhavesh.surveyapp.adapter.SurveyAdapter;
import com.bhavesh.surveyapp.fragment.NewHomeFragment;
import com.bhavesh.surveyapp.utils.SurveyPrefrences;
import com.bhavesh.surveyapp.utils.Utills;
import com.cnx.surveyapp.R;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartSurveyActivity extends AppCompatActivity implements StartSurveyAdapter.Callback {
    public NavigationView navigationView;
    public DrawerLayout mdrawerLayout;
    private ImageView iv_user_image;
    private TextView tv_user_name;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String currentFragName;
    @BindView(R.id.rv_start_survey)
    RecyclerView rv_start_survey;
    StartSurveyAdapter adapter;
    @BindView(R.id.start_survey)
    Button start_survey;
    ActionBarDrawerToggle toggle;
    public static int currentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_page);
        ButterKnife.bind(this);
        initialize();
        settoolbar();
    }

    private void settoolbar() {
        tv_name.setText("Start Survey");

    }

    private void initialize() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        loadFragment(new NewHomeFragment(), true);
        setupDrawer();
        start_survey.setVisibility(View.GONE);
        adapter = new StartSurveyAdapter(this);
        rv_start_survey.setAdapter(adapter);
    }

    private void setupDrawer() {
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        iv_user_image = headerView.findViewById(R.id.iv__nav_player_image);
        tv_user_name = headerView.findViewById(R.id.tv__nav_player_name);
        mdrawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, mdrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mdrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(menuItem -> {
                    Fragment fragment;
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            fragment = new NewHomeFragment();
                            loadFragment(fragment, true);
                            mdrawerLayout.closeDrawer(GravityCompat.START);
                            break;
                        case R.id.navigation_about:
                            startActivity(new Intent(this, AboutUs.class));
                            mdrawerLayout.closeDrawer(GravityCompat.START);
                            break;
                        case R.id.navigation_privacy:
                            startActivity(new Intent(this, Privacy.class));
                            mdrawerLayout.closeDrawer(GravityCompat.START);
                            break;
                        case R.id.navigation_terms:
                            startActivity(new Intent(this, Terms.class));
                            mdrawerLayout.closeDrawer(GravityCompat.START);
                            break;
                        case R.id.navigation_log:
                            SurveyPrefrences.ClearPreferences(this);
                            startActivity(new Intent(this, LoginActivity.class));
                            finish();
                            break;


                    }
                    mdrawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
        );
    }

    @Override
    public void onBackPressed() {
        int backStackSize = getSupportFragmentManager().getBackStackEntryCount();
        if (mdrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mdrawerLayout.closeDrawer(GravityCompat.START);
        } else if (backStackSize == 1) {
            showBottomDialog();
        } else {
            if (backStackSize == 2) {

            }
            super.onBackPressed();
        }
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alertbox);
        TextView text_yes_exit = dialog.findViewById(R.id.Button_Yes);
        TextView text_No_exit = dialog.findViewById(R.id.Button_No);
        text_yes_exit.setOnClickListener(v -> {
            // gamesLog.appLogs(playerId, HomeActivity.class.getSimpleName(), GameLogConstant.APP_CLOSE, "", "", "", "");
            finishAffinity();
            dialog.dismiss();
        });
        text_No_exit.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public void loadFragment(@NonNull Fragment fragment, boolean clearTillHome) {
        String fragmentName = fragment.getClass().getSimpleName();
        currentFragName = fragmentName;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isFragmentInBackStack(fragmentManager, fragment.getClass().getSimpleName())) {
            // Fragment exists, go back to that fragment
            fragmentManager.popBackStack(fragment.getClass().getSimpleName(), 0);
        } else {
            if (clearTillHome)
                fragmentManager.popBackStack(NewHomeFragment.class.getSimpleName(), 0);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment, fragmentName)
                    .addToBackStack(fragmentName)
                    .commit();
        }
    }

    public static boolean isFragmentInBackStack(final FragmentManager fragmentManager, final String fragmentTagName) {
        for (int entry = 0; entry < fragmentManager.getBackStackEntryCount(); entry++) {
            if (fragmentTagName.equals(fragmentManager.getBackStackEntryAt(entry).getName())) {
                return true;
            }
        }
        return false;
    }

    @OnClick(R.id.start_survey)
    public void onClick(View view) {
        if (view.getId() == R.id.start_survey) {
            startActivity(new Intent(this, UpdateUserInformation.class));

        }
    }

    @Override
    public void onItemClick(int position) {
        currentPosition = position;
        if (position >= 0) {
            start_survey.setVisibility(View.VISIBLE);
        } else {
            start_survey.setVisibility(View.GONE);
        }
    }

    // update item background
    private void setOrderBackground() {
        try {
            View view = null;
            CardView imageView = null;
            view = rv_start_survey.findViewHolderForAdapterPosition(currentPosition).itemView;
            imageView = (CardView) view.findViewById(R.id.cv_main);
            imageView.setBackgroundResource(R.drawable.my_custom_grid);
            orderButton.setBackgroundResource(R.drawable.bg_chip_gray);
            orderButton.setClickable(true);
            currentPosition = -1;
        } catch (Exception ex) {
        }
    }
}
