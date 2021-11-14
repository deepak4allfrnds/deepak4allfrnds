package com.bhavesh.surveyapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.bhavesh.surveyapp.model.ProjectDetails;
import com.bhavesh.surveyapp.model.ProjectResponse;
import com.bhavesh.surveyapp.utils.LoadingDialog;

import com.cnx.surveyapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectProject extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.spnr_project)
    Spinner spnr_project;
    @BindView(R.id.ll_spn_project)
    LinearLayout ll_spn_project;
    private List<ProjectDetails> projectDetails = new ArrayList<>();
    private ArrayList<String> project_name = new ArrayList<>();

    LoadingDialog dialog;
    @BindView(R.id.start_survey)
    Button start_survey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_project_activity);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        dialog = new LoadingDialog(this);
        projectDetails.add(new ProjectDetails("1","LOk Sabha"));
        projectDetails.add(new ProjectDetails("2","Rajya Sabha"));
        projectDetails.add(new ProjectDetails("2","Legislative"));
        //  getResult();
        //  getResult();
        spnr_project.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String section_selected = parent.getSelectedItem() + "";
                    String name = projectDetails.get(position - 1).getProject_name();
                    start_survey.setVisibility(View.VISIBLE);
                    //               getSurveyResult(id, section_selected,name);
                }
                start_survey.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tv_name.setText("Assigned Project");
        iv_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void getSurveyResult(long id, String section_selected, String name) {
        // userViewModel.callQuizResponse("id","selected_id",this);
    }

    private void getResult() {

    }
    @OnClick(R.id.start_survey)
    public void onClick(View view) {
        if (view.getId() == R.id.start_survey) {
            startActivity(new Intent(this, UpdateUserInformation.class));

        }
    }
}
