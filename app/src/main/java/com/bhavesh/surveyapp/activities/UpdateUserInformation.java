package com.bhavesh.surveyapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
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
import android.view.View;
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
import com.bhavesh.surveyapp.viewModel.UserViewModel;
import com.cnx.surveyapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

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
    @BindView(R.id.et_district)
    EditText et_district;
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
    RoundedImageView circleImageView;
    private String[] valuesGender = new String[]{MALE, FEMALE};
    private String[] valuesCaste = new String[]{SC, ST, OBC, GENERAL, OTHERS};
    private String[] valuesAge = new String[]{ABOVE_18, ABOVE_25, ABOVE_40, ABOVE_60};
    private String[] valuesReligion = new String[]{HINDU, MUSLIM, SIKH, OTHERS};
    private String[] valuesArea = new String[]{RURAL, URBAN, SEMI_URBAN};
    private String[] valuesEducation = new String[]{MASTER, POST_GRADUATE, GRADUATE, EDU_12, EDU_10, EDU_8, ILLETRATE};
    private String[] valuesEmployed = new String[]{PRIVATE, GOVT, SELF, HOUSEWIFE};
    private Dialog myDialog;
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
    private UserViewModel userViewModel;
    //location
    private FusedLocationProviderClient fusedLocationClient;
    LoadingDialog dialog;
    int PERMISSION_ALL = 1;
    String getLat, getlongs;
    public String gender_value, caste_value, age_value, religion_value, area_value, education_value, employed_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user_information);
        ButterKnife.bind(this);
        settoolbar();
        intialize();
    }

    private void settoolbar() {
        tv_name.setText("Update Information");
        iv_back.setVisibility(View.GONE);
    }

    private void intialize() {
        userViewModel = ViewModelProviders.of(UpdateUserInformation.this).get(UserViewModel.class);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        dialog = new LoadingDialog(this);
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
//            if(validation()){
//                startActivity(new Intent(this, QuestionAnswerSurvey.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//            }
            startActivity(new Intent(this, QuestionAnswerSurvey.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        }
    }

    private boolean validation() {
        return true;
    }
}
