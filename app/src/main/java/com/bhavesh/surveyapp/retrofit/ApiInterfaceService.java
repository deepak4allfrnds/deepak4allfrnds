package com.bhavesh.surveyapp.retrofit;



import com.github.mikephil.charting.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiInterfaceService {

    private static final String URL = "http://demo2.evirtualservices.co/islamic-time-management/site/services/index/";
    private static final String URL_BAR = "https://raw.githubusercontent.com/paveltech/BarchartExample/master/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS);

    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    private static OkHttpClient.Builder getHttpClient() {
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            httpClient.addInterceptor(new LogInterceptor());
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            httpClient.addInterceptor(interceptor);
        }
        return httpClient;
    }

    private static Retrofit retrofit;

    public static WebApi getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getHttpClient().build())
                    .build();
        }
        return retrofit.create(WebApi.class);
    }

}
