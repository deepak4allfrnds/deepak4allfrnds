package com.bhavesh.surveyapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuizModel {
    @SerializedName("questionId")
    private String questionId;
    @SerializedName("type")
    private String type;
    @SerializedName("question")
    private String question;
    @SerializedName("answer")
    private String answer;

    private String totalTime;

    public String getAnswer() {
        return answer;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setContestQuestionData(List<AnswerModel> contestQuestionData) {
        this.contestQuestionData = contestQuestionData;
    }

    @SerializedName("option")
    private List<AnswerModel> contestQuestionData;

    private String answerSelected = "";

    public String getAnswerSelected() {
        return answerSelected;
    }

    public void setAnswerSelected(String answer) {
        this.answerSelected = answer;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public List<AnswerModel> getContestQNAData() {
        return contestQuestionData;
    }
}
