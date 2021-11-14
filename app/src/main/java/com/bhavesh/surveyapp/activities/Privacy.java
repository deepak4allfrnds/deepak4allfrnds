package com.bhavesh.surveyapp.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cnx.surveyapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Privacy extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        ButterKnife.bind(this);
        intialize();
    }

    private void intialize() {
    tv_name.setText("Help");
    iv_back.setOnClickListener(v -> {
        onBackPressed();
    });
    }


}
