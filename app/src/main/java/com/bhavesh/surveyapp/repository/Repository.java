package com.bhavesh.surveyapp.repository;

import com.bhavesh.surveyapp.model.LoginResponse;
import com.bhavesh.surveyapp.retrofit.ApiInterfaceService;
import com.bhavesh.surveyapp.viewModel.UserAuthListener;

import retrofit2.Call;

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
}
