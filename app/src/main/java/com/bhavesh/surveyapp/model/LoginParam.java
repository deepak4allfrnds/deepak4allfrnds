package com.bhavesh.surveyapp.model;

public class LoginParam {
    String username;
    String password;
    String fcmToken;

    public LoginParam(String username, String password, String fcmToken) {
        this.username = username;
        this.password = password;
        this.fcmToken = fcmToken;
    }
}
