package com.bhavesh.surveyapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.bhavesh.surveyapp.model.LoginResponse;
import com.bhavesh.surveyapp.utils.Global;
import com.bhavesh.surveyapp.utils.LoadingDialog;
import com.bhavesh.surveyapp.utils.SessionManager;
import com.bhavesh.surveyapp.utils.Utills;
import com.bhavesh.surveyapp.viewModel.UserAuthListener;
import com.bhavesh.surveyapp.viewModel.UserViewModel;
import com.cnx.surveyapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements UserAuthListener {
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
    private String mobile, password_String;
    String mobile_string, password_string;
    private UserViewModel userViewModel;
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
        userViewModel = ViewModelProviders.of(LoginActivity.this).get(UserViewModel.class);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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

    private void getCheckLocation() {
        if (checkLocation()) {
            if (!Global.GpsEnable(this)) {
                Global.showGPSDisabledAlertToUser(this);
            } else {
                lastLocation();
            }
        } else Global.showSettingsDialog(this);
    }

    private void getAddress(double lat, Double longs) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, longs, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            getLat = String.valueOf(lat);
            getlongs = String.valueOf(longs);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    private void lastLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            try {
                                getAddress(location.getLatitude(), location.getLongitude());

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private boolean checkLocation() {
        int courcelocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int fineLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (courcelocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (fineLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_ALL);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                lastLocation();
                return;
            }
            default:
                this.finish();
        }
    }


    private void settoolbar() {
        tv_name.setText("Login");
        iv_back.setVisibility(View.GONE);
    }

    private boolean validation() {
        mobile = edttxt_email.getText().toString().trim();
        password_string = edttxt_password.getText().toString().trim();
        if (password_String.equalsIgnoreCase("")) {
            edttxt_email.setError(Html.fromHtml("<font color='red'>Enter phone number</font>"));
            return false;
        } else if (password_String.length() < 10) {
            edttxt_email.setError(Html.fromHtml("<font color='red'>Enter Correct phone number</font>"));
            return false;
        } else if (!Global.password(password_string)) {
            //    edttxt_email.setError(null);
            edttxt_password.setError(Html.fromHtml("<font color='red'>Enter correct password</font>"));
            return false;
        }
        return true;

    }

    @OnClick(R.id.btn_login)
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
//            if (validation()) {
//                userViewModel.hitVendorLoginApi(mobile_string, password_String, SessionManager.get_device_token(prefs), getLat, getlongs, this);
//            } else {
//                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
//            }
            startActivity(new Intent(this, StartSurveyActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }
    }

    @Override
    public void onStarted() {
        dialog.show();
    }

    @Override
    public void onSuccess(LiveData<LoginResponse> user) {
        dialog.hide();
        if (user != null) {
            startActivity(new Intent(this, StartSurveyActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        } else {
            Global.msgDialog(this, user.getValue().getErrorMessage());
        }
    }

    @Override
    public void onFailure(String message) {
        dialog.hide();
        Global.showToast(this, message);
    }
}
