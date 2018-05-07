package com.example.chenchongkang.memeapplication.model;

/**
 * Created by chenchongkang on 2018/5/7.
 */

public class EvaluationBean {
    private Integer evaluationID;
    private Integer userID;
    private Integer memeID;
    private Double evaluations;
    private String evaluationTime;

    public EvaluationBean() {
    }

    public EvaluationBean(Integer evaluationID, Integer userID, Integer memeID, Double evaluations, String evaluationTime) {
        this.evaluationID = evaluationID;
        this.userID = userID;
        this.memeID = memeID;
        this.evaluations = evaluations;
        this.evaluationTime = evaluationTime;
    }

    public Integer getEvaluationID() {
        return evaluationID;
    }

    public void setEvaluationID(Integer evaluationID) {
        this.evaluationID = evaluationID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getMemeID() {
        return memeID;
    }

    public void setMemeID(Integer memeID) {
        this.memeID = memeID;
    }

    public Double getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(Double evaluations) {
        this.evaluations = evaluations;
    }

    public String getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(String evaluationTime) {
        this.evaluationTime = evaluationTime;
    }
}
