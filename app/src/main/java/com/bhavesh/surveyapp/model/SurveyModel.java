package com.bhavesh.surveyapp.model;

public class SurveyModel {
    private String name;
    private boolean isSelected;

    public SurveyModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
