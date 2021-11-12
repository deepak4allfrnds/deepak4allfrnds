package com.bhavesh.surveyapp.viewModel;

import androidx.lifecycle.LiveData;

public interface ResultAuthListner<T> {
    void onStarted();

    void onResultSuccess(LiveData<T> data);

    void onFailure(String message);
}
