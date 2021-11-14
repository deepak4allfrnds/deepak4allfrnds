package com.bhavesh.surveyapp.model;

import com.google.gson.annotations.SerializedName;

public class ProjectDetails {
    @SerializedName("id")
    private String id;
    @SerializedName("project_name")
    private String project_name;

    public ProjectDetails(String id, String project_name) {
        this.id = id;
        this.project_name = project_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }
}

