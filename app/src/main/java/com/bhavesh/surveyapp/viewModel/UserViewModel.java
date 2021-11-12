package com.bhavesh.surveyapp.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bhavesh.surveyapp.model.LoginResponse;
import com.bhavesh.surveyapp.repository.ApiUtils;
import com.bhavesh.surveyapp.repository.Repository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {

    // hit login api
    public void hitVendorLoginApi(String username, String password, String deviceToken, String latitiude, String longitude, final UserAuthListener authListener) {
        authListener.onStarted();
        Repository.getInstance().executeLogin(username, password, deviceToken,latitiude,longitude, authListener).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    authListener.onSuccess(new MutableLiveData<LoginResponse>(response.body()));
                    Log.e("response.body()", response.body().toString());
                } else {
                    switch (response.code()) {
                        case 404:
                            authListener.onFailure("not found");
                            break;
                        case 500:
                            authListener.onFailure("server Error");
                            break;
                        default:
                            authListener.onFailure("unknown error ");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                authListener.onFailure(" network failure :( inform the user and possibly retry");
            }
        });

//        authListener.onSuccess(responce);
    }
    //quiz Response
    public void callQuizResponse(String id, String level,AuthListener authListener) {
        authListener.onStarted();
        Repository.getInstance().executeQuiz(id,level, authListener);
    }
    // quiz Result
    public void callQuizResult(String id, String type, String totalTime,String answerlist,ResultAuthListner authListener) {
        authListener.onStarted();
        Repository.getInstance().executeResult(id,type,totalTime,answerlist, authListener);
    }

}
