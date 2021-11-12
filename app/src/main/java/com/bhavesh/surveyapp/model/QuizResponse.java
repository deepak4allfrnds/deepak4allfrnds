package com.bhavesh.surveyapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuizResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("msg")
    private String message;
    @SerializedName("Total")
    private String Total;
    @SerializedName("correct")
    private String correct;
    @SerializedName("result_message")
    private String result_message;

    public String getResult_message() {
        return result_message;
    }

    public String getTotal() {
        return Total;
    }

    public String getCorrect() {
        return correct;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public List<QuizModel> getData() {
        return data;
    }

    @SerializedName("data")
    private List<QuizModel> data;

}
