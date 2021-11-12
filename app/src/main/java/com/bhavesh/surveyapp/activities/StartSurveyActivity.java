package com.bhavesh.surveyapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bhavesh.surveyapp.adapter.StartSurveyAdapter;
import com.bhavesh.surveyapp.adapter.SurveyAdapter;
import com.bhavesh.surveyapp.utils.Utills;
import com.cnx.surveyapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartSurveyActivity extends AppCompatActivity implements StartSurveyAdapter.Callback {
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_start_survey)
    RecyclerView rv_start_survey;
    StartSurveyAdapter adapter;
    @BindView(R.id.start_survey)
    Button start_survey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_survey);
        ButterKnife.bind(this);
        initialize();
        settoolbar();
    }

    private void settoolbar() {
        tv_name.setText("Start Survey");
        iv_back.setVisibility(View.GONE);
    }

    private void initialize() {
        start_survey.setVisibility(View.GONE);
        adapter = new StartSurveyAdapter(this);
        rv_start_survey.setAdapter(adapter);
    }

    @OnClick(R.id.start_survey)
    public void onClick(View view) {
        if (view.getId() == R.id.start_survey) {
            startActivity(new Intent(this, UpdateUserInformation.class));

        }
    }

    @Override
    public void onItemClick(int postion) {
        if (postion >= 0) {
            start_survey.setVisibility(View.VISIBLE);
        } else {
            start_survey.setVisibility(View.GONE);
        }
    }
}
