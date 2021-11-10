package com.bhavesh.surveyapp.repository;

import com.bhavesh.surveyapp.retrofit.WebApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {
    public static String BASE_URL="http://65.1.89.89:3003/";
    private static WebApi requestAPI;
    public static Retrofit getClient() {
        String baseUrl = BASE_URL;
//        if (BuildConfig.DEBUG){
//            baseUrl = SharedPrefreances.getSharedPreferenceString(Meeast.context(), SharedPrefreances.BASE_URL_DUMMY);
//        }
        Retrofit retrofit = null;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
