package com.bhavesh.surveyapp.retrofit;

import com.bhavesh.surveyapp.model.LoginParam;
import com.bhavesh.surveyapp.model.LoginResponse;
import com.bhavesh.surveyapp.model.QuizResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WebApi {
    @FormUrlEncoded
    @POST(".")
    Call<LoginResponse> login(
            @Field("action") String action,
            @Field("email") String username,
            @Field("password") String password,
            @Field("deviceToken") String deviceToken,
            @Field("device") String device,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude);
    @FormUrlEncoded
    @POST(".")
    Call<QuizResponse> quizResult(
            @Field("action") String action,
            @Field("userId") String id,
            @Field("type") String type,
            @Field("totalTime") String totalTime,
            @Field("anshwerlist") String list
    );

    @FormUrlEncoded
    @POST(".")
    Call<QuizResponse> quizResponse(
            @Field("action") String action,
            @Field("type") String id,
            @Field("level") String levelId
    );

}
