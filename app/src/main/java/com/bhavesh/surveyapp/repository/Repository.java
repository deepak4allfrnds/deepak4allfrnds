package com.bhavesh.surveyapp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.bhavesh.surveyapp.model.LoginResponse;
import com.bhavesh.surveyapp.model.QuizResponse;
import com.bhavesh.surveyapp.retrofit.ApiInterfaceService;
import com.bhavesh.surveyapp.viewModel.AuthListener;
import com.bhavesh.surveyapp.viewModel.ResultAuthListner;
import com.bhavesh.surveyapp.viewModel.UserAuthListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static Repository repository;

    public static Repository getInstance() {
        if (repository != null) {
            return repository;
        } else {
            repository = new Repository();
            return repository;
        }
    }

    public Call<LoginResponse> executeLogin(String username, String password, String deviceToken, String latitiude, String longitude, final UserAuthListener authListener) {
        return ApiInterfaceService.getApiService().login("login", username, password, deviceToken, "Android", latitiude, longitude);

    }

    // Quiz Response
    public void executeQuiz(String id,String level, final AuthListener authListener) {
        //noinspection NullableProblems
        ApiInterfaceService.getApiService().quizResponse("quizlist", id,level)
                .enqueue(new Callback<QuizResponse>() {
                    @Override
                    public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {
                        if (response.isSuccessful()) {
                            authListener.onSuccess(new MutableLiveData(response.body()));
//                            loginResponse.setValue(response.body());

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
                    public void onFailure(Call<QuizResponse> call, Throwable t) {
                        authListener.onFailure(" network failure :( inform the user and possibly retry");
                        Log.e("res onFailure(): ", "network failure :( inform the user and possibly retry");
                    }
                });
        //    return response;
    }
    // Quiz Result

    public void executeResult(String id, String type,String totalTime, String answerList, final ResultAuthListner authListener) {
        //noinspection NullableProblems
        ApiInterfaceService.getApiService().quizResult("addanswer", id, "2", totalTime,answerList)
                .enqueue(new Callback<QuizResponse>() {
                    @Override
                    public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {
                        if (response.isSuccessful()) {
                            authListener.onResultSuccess(new MutableLiveData(response.body()));
//                            loginResponse.setValue(response.body());

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
                    public void onFailure(Call<QuizResponse> call, Throwable t) {
                        authListener.onFailure(" network failure :( inform the user and possibly retry");
                        Log.e("res onFailure(): ", "network failure :( inform the user and possibly retry");
                    }
                });
        //    return response;
    }

}
