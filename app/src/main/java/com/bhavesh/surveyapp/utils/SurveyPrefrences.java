package com.bhavesh.surveyapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SurveyPrefrences {
    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static void ClearPreferences(Context context) {
        getSharedPreferences(context).edit().clear().apply();
    }
    public static void setUserEmail(Context context, String userId) {
        getSharedPreferences(context).edit().putString(Constant.AGENT_EMAIL, userId).apply();
    }

    public static String getUserEmail(Context context) {
        return getSharedPreferences(context).getString(Constant.AGENT_EMAIL, "");
    }

    public static void setUserId(Context context, String userId) {
        getSharedPreferences(context).edit().putString(Constant.AGENT_EMAIL, userId).apply();
    }

    public static String getUserId(Context context) {
        return getSharedPreferences(context).getString(Constant.AGENT_EMAIL, "");
    }

}
