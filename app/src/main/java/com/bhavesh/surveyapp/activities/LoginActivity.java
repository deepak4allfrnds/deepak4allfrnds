package com.bhavesh.surveyapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bhavesh.surveyapp.model.LoginResponse;
import com.bhavesh.surveyapp.retrofit.ApiUtils;
import com.bhavesh.surveyapp.retrofit.WebApi;
import com.bhavesh.surveyapp.utils.Global;
import com.bhavesh.surveyapp.utils.LoadingDialog;
import com.cnx.surveyapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    int PERMISSION_ALL = 1;
    String getLat, getlongs;
    @BindView(R.id.edttxt_email)
    EditText edttxt_email;
    @BindView(R.id.edttxt_password)
    EditText edttxt_password;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pass_check)
    CheckBox pass_check;
    LoadingDialog dialog;
    private FusedLocationProviderClient fusedLocationClient;
    private String email, password;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        settoolbar();
        intialize();

    }

    private void intialize() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        dialog = new LoadingDialog(this);
        pass_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edttxt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edttxt_password.setSelection(edttxt_password.length());
                } else {
                    edttxt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edttxt_password.setSelection(edttxt_password.length());
                }
            }
        });


    }


    private void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void settoolbar() {
        tv_name.setText("Login");
        iv_back.setVisibility(View.GONE);
    }

    private boolean validation() {
        email = edttxt_email.getText().toString().trim();
        password = edttxt_password.getText().toString().trim();
        if (email.equalsIgnoreCase("")) {
            edttxt_email.setError(Html.fromHtml("<font color='red'>Enter Email Address</font>"));
            return false;
//        } else if (!Global.isValidEmail(email)) {
//            edttxt_email.setError(Html.fromHtml("<font color='red'>Enter Valid Email Address</font>"));
//            return false;
       }else if (password.equalsIgnoreCase("")) {
            edttxt_password.setError(Html.fromHtml("<font color='red'>Enter phone number</font>"));
            return false;
        }
        return true;

    }

    @OnClick(R.id.btn_login)
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            if (validation()) {
                dialog.show();
                HashMap<String, String> map = new HashMap<>();
                map.put("name", email);
                map.put("pass", password);
                WebApi webApi = ApiUtils.getClient().create(WebApi.class);
                Call<LoginResponse> call = webApi.agent_login(map);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        dialog.hide();
                        if (response.body().getStatus().equalsIgnoreCase("success")) {

                            startActivity(new Intent(LoginActivity.this, StartSurveyActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            finish();
                        } else {
                            Global.showToast(LoginActivity.this, "email and password is invalid");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                       dialog.hide();
                        Global.showToast(LoginActivity.this, "Api Failure");
                    }
                });
            }
        }
    }


}
