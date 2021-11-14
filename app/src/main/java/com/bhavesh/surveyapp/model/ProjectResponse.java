package com.bhavesh.surveyapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectResponse {
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private List<ProjectDetails> data = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ProjectDetails> getData() {
        return data;
    }

    public void setData(List<ProjectDetails> data) {
        this.data = data;
    }
}

