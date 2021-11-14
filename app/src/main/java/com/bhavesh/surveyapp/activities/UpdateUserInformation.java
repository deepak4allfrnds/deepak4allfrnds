package com.bhavesh.surveyapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.bhavesh.surveyapp.utils.BaseClass;
import com.bhavesh.surveyapp.utils.Global;
import com.bhavesh.surveyapp.utils.ImageFilePath;
import com.bhavesh.surveyapp.utils.ImageLoaderUtils;
import com.bhavesh.surveyapp.utils.LoadingDialog;
import com.bhavesh.surveyapp.utils.RealPathUtils;
import com.bhavesh.surveyapp.utils.Utills;
import com.cnx.surveyapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;
import static com.bhavesh.surveyapp.utils.Constant.Area.RURAL;
import static com.bhavesh.surveyapp.utils.Constant.Area.SEMI_URBAN;
import static com.bhavesh.surveyapp.utils.Constant.Area.URBAN;
import static com.bhavesh.surveyapp.utils.Constant.Caste.GENERAL;
import static com.bhavesh.surveyapp.utils.Constant.Caste.OBC;
import static com.bhavesh.surveyapp.utils.Constant.Caste.OTHERS;
import static com.bhavesh.surveyapp.utils.Constant.Caste.SC;
import static com.bhavesh.surveyapp.utils.Constant.Caste.ST;
import static com.bhavesh.surveyapp.utils.Constant.Education.EDU_10;
import static com.bhavesh.surveyapp.utils.Constant.Education.EDU_12;
import static com.bhavesh.surveyapp.utils.Constant.Education.EDU_8;
import static com.bhavesh.surveyapp.utils.Constant.Education.GRADUATE;
import static com.bhavesh.surveyapp.utils.Constant.Education.ILLETRATE;
import static com.bhavesh.surveyapp.utils.Constant.Education.MASTER;
import static com.bhavesh.surveyapp.utils.Constant.Education.POST_GRADUATE;
import static com.bhavesh.surveyapp.utils.Constant.FEMALE;
import static com.bhavesh.surveyapp.utils.Constant.MALE;
import static com.bhavesh.surveyapp.utils.Constant.Occuptant.GOVT;
import static com.bhavesh.surveyapp.utils.Constant.Occuptant.HOUSEWIFE;
import static com.bhavesh.surveyapp.utils.Constant.Occuptant.PRIVATE;
import static com.bhavesh.surveyapp.utils.Constant.Occuptant.SELF;
import static com.bhavesh.surveyapp.utils.Constant.Religion.CHRISTIAN;
import static com.bhavesh.surveyapp.utils.Constant.Religion.HINDU;
import static com.bhavesh.surveyapp.utils.Constant.Religion.MUSLIM;
import static com.bhavesh.surveyapp.utils.Constant.Religion.SIKH;
import static com.bhavesh.surveyapp.utils.Constant.ageGroup.ABOVE_18;
import static com.bhavesh.surveyapp.utils.Constant.ageGroup.ABOVE_25;
import static com.bhavesh.surveyapp.utils.Constant.ageGroup.ABOVE_40;
import static com.bhavesh.surveyapp.utils.Constant.ageGroup.ABOVE_60;

public class UpdateUserInformation extends AppCompatActivity {
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_full_name)
    EditText et_full_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_location)
    EditText et_location;
    @BindView(R.id.et_mobile)
    EditText et_mobile;
    @BindView(R.id.et_Otp)
    EditText et_Otp;
    @BindView(R.id.et_state)
    EditText et_state;
    @BindView(R.id.tv_gender)
    TextView tv_gender;
    @BindView(R.id.tv_age)
    TextView tv_age;
    @BindView(R.id.tv_occupation)
    TextView tv_occupation;
    @BindView(R.id.tv_education)
    TextView tv_education;
    @BindView(R.id.tv_caste)
    TextView tv_caste;
    @BindView(R.id.tv_religion)
    TextView tv_religion;
    @BindView(R.id.tv_district)
    TextView tv_district;
    @BindView(R.id.tv_area)
    TextView tv_area;


    @BindView(R.id.iv_user)
    CircularImageView circleImageView;
    private String[] valuesGender = new String[]{MALE, FEMALE, OTHERS};
    private String[] valuesCaste = new String[]{SC, ST, OBC, GENERAL, OTHERS};
    private String[] valuesAge = new String[]{ABOVE_18, ABOVE_25, ABOVE_40, ABOVE_60};
    private String[] valuesReligion = new String[]{HINDU, MUSLIM, SIKH, CHRISTIAN, OTHERS};
    private String[] valuesArea = new String[]{RURAL, URBAN, SEMI_URBAN};
    private String[] valuesEducation = new String[]{MASTER, POST_GRADUATE, GRADUATE, EDU_12, EDU_10, EDU_8, ILLETRATE};
    private String[] valuesEmployed = new String[]{PRIVATE, GOVT, SELF, HOUSEWIFE, OTHERS};
    private Dialog myDialog;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int REQUEST_CAMERA = 1;
    private static final int RESULT_LOAD = 2;
    private static final int REQUEST_STORAGE_PERMISSION = 1;
    private Uri imageUri;
    private String mCurrentPhotoPath;
    private String file_path = "";
    private String currentFrag;
    private File fileToUpload;
    private File profileCardFile;
    int requestType = 0;
    //location
    private FusedLocationProviderClient fusedLocationClient;
    LoadingDialog dialog;
    int PERMISSION_ALL = 1;
    String getLat, getlongs;
    public String gender_value, caste_value, age_value, religion_value, area_value, education_value, employed_value;
    public String first_name, email, location, mobileNumber, otp;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user_information);
        ButterKnife.bind(this);
        settoolbar();
        intialize();
        getCheckLocation();
    }

    private void settoolbar() {
        tv_name.setText("Update Information");
        iv_back.setVisibility(View.GONE);
    }

    private void intialize() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        dialog = new LoadingDialog(this);
        mCheckPermission();
        tv_gender.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Select Gender:");
            alertDialogBuilder.setItems(valuesGender, (dialog, which) -> {
                gender_value = Arrays.asList(valuesGender).get(which);
                tv_gender.setText(gender_value);
            });
            AlertDialog dialog = alertDialogBuilder.create();
            dialog.show();
        });
        tv_caste.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Select Caste:");
            alertDialogBuilder.setItems(valuesCaste, (dialog, which) -> {
                caste_value = Arrays.asList(valuesCaste).get(which);
                tv_caste.setText(caste_value);
            });
            AlertDialog dialog = alertDialogBuilder.create();
            dialog.show();
        });
        tv_age.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Select Gender:");
            alertDialogBuilder.setItems(valuesAge, (dialog, which) -> {
                age_value = Arrays.asList(valuesAge).get(which);
                tv_age.setText(age_value);
            });
            AlertDialog dialog = alertDialogBuilder.create();
            dialog.show();
        });
        tv_area.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Select Area:");
            alertDialogBuilder.setItems(valuesArea, (dialog, which) -> {
                area_value = Arrays.asList(valuesArea).get(which);
                tv_area.setText(area_value);
            });
            AlertDialog dialog = alertDialogBuilder.create();
            dialog.show();
        });
        tv_occupation.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Select Occupation:");
            alertDialogBuilder.setItems(valuesEmployed, (dialog, which) -> {
                employed_value = Arrays.asList(valuesEmployed).get(which);
                tv_occupation.setText(employed_value);
            });
            AlertDialog dialog = alertDialogBuilder.create();
            dialog.show();
        });
        tv_education.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Select Education:");
            alertDialogBuilder.setItems(valuesEducation, (dialog, which) -> {
                education_value = Arrays.asList(valuesEducation).get(which);
                tv_education.setText(education_value);
            });
            AlertDialog dialog = alertDialogBuilder.create();
            dialog.show();
        });
        tv_religion.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Select Religion:");
            alertDialogBuilder.setItems(valuesReligion, (dialog, which) -> {
                religion_value = Arrays.asList(valuesReligion).get(which);
                tv_religion.setText(religion_value);
            });
            AlertDialog dialog = alertDialogBuilder.create();
            dialog.show();
        });


    }

    private void mCheckPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean phoneStateAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && phoneStateAccepted) {
                        Log.d("PERRRR", "Permission Granted, Now you can access location data and camera.");
                        //  SharedPrefreances.setSharedPreferenceString(this, SharedPrefreances.SHOW_PERMISSION, "false");
                    }
                }

                break;
        }
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
            et_location.setText(address);
            et_state.setText(state);
            tv_district.setText(city);
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

    @Override
    protected void onResume() {
        super.onResume();
        checkLocation();
    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void uploadFilesCamera(String title) {
        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_upload_file);
        TextView textPopUp = myDialog.findViewById(R.id.textPopUp);
        textPopUp.setText(title);
        ImageView imageView3 = myDialog.findViewById(R.id.imageView3);
        imageView3.setOnClickListener(this::onClick);
        LinearLayout camera = myDialog.findViewById(R.id.camera);
        LinearLayout attach = myDialog.findViewById(R.id.attach);
        attach.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, RESULT_LOAD);
        });
        camera.setOnClickListener(v -> {
            try {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_STORAGE_PERMISSION);
                } else {
                    launchCamera(REQUEST_CAMERA);
                }
            } catch (Exception e) {
                Timber.e(e);
            }
        });
        imageView3.setOnClickListener(v -> {
            if (myDialog.isShowing()) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }


    private void launchCamera(int requestImage) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "MyPicture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
        imageUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, requestImage);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp;
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File file = File.createTempFile(
                imageFileName,   //prefix
                ".jpg",          //suffix
                storageDir       //directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = file.getAbsolutePath();
        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CAMERA:          //from file manager
                if (myDialog.isShowing()) {
                    myDialog.dismiss();
                }
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        saveImage2(imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case RESULT_LOAD:
                if (myDialog.isShowing()) {
                    myDialog.dismiss();
                }
                if (resultCode == RESULT_OK) {
                    try {
                        String filePath = BaseClass.getMimeType(this, data.getData());
                        if (filePath.equalsIgnoreCase("png") || filePath.equalsIgnoreCase("jpeg") ||
                                filePath.contains("jpg")) {
                            if (myDialog.isShowing()) {
                                myDialog.dismiss();
                            }
                        }
                        imageUri = data.getData();
                        saveImage2(imageUri);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

        }

    }


    private void saveImage2(Uri image) {
        try {
            try {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                fileToUpload = new File(image.getPath());
                RealPathUtils filePathHelper = new RealPathUtils();
                String filePath;
                if (Build.VERSION.SDK_INT >= 23) {
                    if (filePathHelper.getPathnew(image, this) != null) {
                        filePath = filePathHelper.getPathnew(image, this).toLowerCase();
                    } else {
                        filePath = filePathHelper.getFilePathFromURI(image, this).toLowerCase();
                    }
                } else {
                    filePath = filePathHelper.getPath(image, this).toLowerCase();
                }
                mCurrentPhotoPath = ImageFilePath.getPath(this, image);
                fileToUpload = new File(mCurrentPhotoPath);
                file_path = fileToUpload.getAbsolutePath();
                ImageLoaderUtils.load(image.toString(), circleImageView);
                //  myBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
            } catch (Exception e) {
                Timber.e(e);
            }


        } catch (Exception e) {
            Timber.e(e);
        }
    }


    private String getFileName(Uri uri) {
        String realPath = uri.getPath();
        Timber.d("realPath %s", realPath);
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
                Timber.d("ResulTwo %s", result);
            }
        }
        return result;
    }

    @OnClick({R.id.tv_change_avatar, R.id.btn_update})
    public void onClick(View view) {
        if (view.getId() == R.id.tv_change_avatar) {// showFileChooser1();
            uploadFilesCamera("Upload Profile");
        } else if (view.getId() == R.id.btn_update) {
            if(validation()){
                HashMap<String, String> map = new HashMap<>();
                map.put("name", first_name);
                map.put("email", email);
                map.put("location", location);
                map.put("mobile", mobileNumber);
                map.put("otp", otp);
                map.put("gender", gender_value);
                map.put("age", age_value);
                map.put("education", education_value);
                map.put("occupation", education_value);
                map.put("caste", caste_value);
                map.put("religion", religion_value);
                map.put("state", );
                map.put("district", password);
                map.put("region", religion_value);
                map.put("area", area_value);

                startActivity(new Intent(this, QuestionAnswerSurvey.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

            }
            startActivity(new Intent(this, QuestionAnswerSurvey.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        }
    }

    private boolean validation() {
        first_name = et_full_name.getText().toString();
        mobileNumber = et_mobile.getText().toString();
        otp = et_Otp.getText().toString();
        if (first_name.equalsIgnoreCase("")) {
            et_full_name.setError(Html.fromHtml("<font color='red'>Enter Full Name</font>"));
            return false;
        } else if (mobileNumber.equalsIgnoreCase("")) {
            et_mobile.setError(Html.fromHtml("<font color='yellow'>Enter Phone Number</font>"));
            return false;
        }else if(!Global.checkMobile(mobileNumber)){
            et_mobile.setError(Html.fromHtml("<font color='yellow'>Enter Valid number</font>"));
        }else if(otp.equalsIgnoreCase("")){
            et_Otp.setError(Html.fromHtml("<font color='yellow'>Enter Valid Otp</font>"));
        }


        return true;
    }
}
