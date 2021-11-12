package com.bhavesh.surveyapp.utils;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BaseClass {
    public static BaseClass baseClass;
    private static String hourTime;
    private static String minutes;
    public Context context;
    protected ProgressDialog progressDialog;
    private int mYear;

    public BaseClass(Context context) {
        this.context = context;
    }

    public static BaseClass getInstance(Context context) {
        if (baseClass == null)
            baseClass = new BaseClass(context);
        return baseClass;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }

    public void logLists(Object obj, String s) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        Log.w(s, gson.toJson(obj));
    }


    public String formatdate(String fdate) {
        String datetime = null;
        DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date convertedDate = inputFormat.parse(fdate);
            datetime = d.format(convertedDate);
        } catch (ParseException e) {
        }
        return datetime;
    }

    public void createDatePickerDialog(Context context, final TextView tv, String dob) {
        final Calendar c2 = Calendar.getInstance();
        if (dob != null) {
            mYear = 2008;
        } else {
            mYear = c2.get(Calendar.YEAR);
        }
        int mMonth = c2.get(Calendar.MONTH);
        int mDay = c2.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog2 = new DatePickerDialog(context,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String dateDynamic = formatdate(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    String str[] = dateDynamic.split("-");
                    String year6 = (String) str[0];
                    String month6 = (String) str[1];
                    String day6 = (String) str[2];
                    tv.setText(year6 + "-" + month6 + "-" + day6);
                }, mYear, mMonth, mDay);
        datePickerDialog2.show();
    }

    public void createTimePickerDialog(Context context, final TextView time) {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                (view, hourOfDay, minute) -> {
                    if (hourOfDay < 10) {
                        hourTime = "0" + String.valueOf(hourOfDay);
                    } else {
                        hourTime = String.valueOf(hourOfDay);
                    }
                    if (minute < 10) {
                        minutes = "0" + String.valueOf(minute);
                    } else {
                        minutes = String.valueOf(minute);
                    }
                    time.setText(hourTime + ":" + minutes);
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public static String getMimeType(Context context, Uri uri) {
        String extension;
        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }
        return extension;
    }

    public Bitmap getResizedBitmap(Bitmap image, int bitmapWidth, int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight, true);
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.DEFAULT);
        return "data:image/png;base64," + imgString;
    }

    public String getMimeType(Uri uri) {
        String extension;
        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }
        return extension;
    }
}
