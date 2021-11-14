package com.bhavesh.surveyapp.retrofit;

import com.bhavesh.surveyapp.model.LoginParam;
import com.bhavesh.surveyapp.model.LoginResponse;
import com.bhavesh.surveyapp.model.ProjectResponse;
import com.bhavesh.surveyapp.model.QuizResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface WebApi {
    @POST("/mobile_login")
    Call<LoginResponse> agent_login(@Body HashMap<String, String> body);
}
