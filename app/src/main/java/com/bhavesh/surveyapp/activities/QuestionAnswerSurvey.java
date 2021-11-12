package com.bhavesh.surveyapp.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.bhavesh.surveyapp.adapter.SurveyPagerAdapter;
import com.bhavesh.surveyapp.model.QuizModel;
import com.bhavesh.surveyapp.model.QuizResponse;
import com.bhavesh.surveyapp.utils.Global;
import com.bhavesh.surveyapp.viewModel.AuthListener;
import com.bhavesh.surveyapp.viewModel.UserViewModel;
import com.cnx.surveyapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionAnswerSurvey extends AppCompatActivity implements AuthListener<QuizResponse> {
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rl_view_progressbar)
    RelativeLayout rlViewProgressbar;
    @BindView(R.id.tv_total_question)
    TextView tv_total_question;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.btn_finish)
    Button submitAnswer;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    private UserViewModel userViewModel;
    private SharedPreferences prefs;
    private ProgressDialog progress;
    SurveyPagerAdapter mPageAdapter;
    private List<QuizModel> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_answer_survey);
        ButterKnife.bind(this);
        setToolbar();
        intialize();
    }

    private void setToolbar() {
        tv_name.setText("Survey");
        iv_back.setVisibility(View.GONE);

    }

    private void intialize() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        progress = Global.getProgressDialog(this, "Please wait...");
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tv_total_question.setText(position + 1 + "/" + questionList.size());

            }

            @Override
            public void onPageSelected(int position) {
                btn_next.setVisibility(View.GONE);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private void getsurveyResponse() {
        userViewModel.callQuizResponse("2", "2", this);
    }


    @Override
    public void onStarted() {

    }

    @Override
    public void onSuccess(LiveData<QuizResponse> data) {

    }

    @Override
    public void onFailure(String message) {

    }
}
