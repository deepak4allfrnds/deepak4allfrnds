package com.bhavesh.surveyapp.utils;

import android.content.SharedPreferences;

public class SessionManager {
    private static String device_token = "DEVICE_TOKEN";
    public static void save_device_token(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, device_token, value);
    }

    public static String get_device_token(SharedPreferences prefs) {
        return prefs.getString(device_token, "");
    }

    public static void savePreference(SharedPreferences prefs, String key, String value) {
        SharedPreferences.Editor e = prefs.edit();
        e.putString(key, value);
        e.apply();
    }
    public static void savePreference(SharedPreferences prefs, String key, int value) {
        SharedPreferences.Editor e = prefs.edit();
        e.putInt(key, value);
        e.apply();
    }

    public static void savePreference(SharedPreferences prefs, String key, Boolean value) {
        SharedPreferences.Editor e = prefs.edit();
        e.putBoolean(key, value);
        e.apply();
    }



    public static void dataclear(SharedPreferences prefs) {
        SharedPreferences.Editor e = prefs.edit();
        e.clear();
        e.apply();
    }

}
