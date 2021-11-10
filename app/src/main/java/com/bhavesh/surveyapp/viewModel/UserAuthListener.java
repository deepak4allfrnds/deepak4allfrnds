package com.bhavesh.surveyapp.viewModel;

import androidx.lifecycle.LiveData;

import com.bhavesh.surveyapp.model.LoginResponse;
public interface UserAuthListener {

    void onStarted();

    void onSuccess(LiveData<LoginResponse> user);

    void onFailure(String message);


}
