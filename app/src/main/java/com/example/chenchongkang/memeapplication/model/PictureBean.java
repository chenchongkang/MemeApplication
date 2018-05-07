package com.example.chenchongkang.memeapplication.model;

/**
 * Created by chenchongkang on 2018/5/6.
 */

public class PictureBean {
    private Integer imgID;
    private Integer memeID;
    private String memeDescribe;
    private String upTime;
    private String path;

    public PictureBean() {
    }

    public PictureBean(Integer imgID, Integer memeID, String memeDescribe, String upTime, String path) {
        this.imgID = imgID;
        this.memeID = memeID;
        this.memeDescribe = memeDescribe;
        this.upTime = upTime;
        this.path = path;
    }

    public Integer getImgID() {
        return imgID;
    }

    public void setImgID(Integer imgID) {
        this.imgID = imgID;
    }

    public Integer getMemeID() {
        return memeID;
    }

    public void setMemeID(Integer memeID) {
        this.memeID = memeID;
    }

    public String getMemeDescribe() {
        return memeDescribe;
    }

    public void setMemeDescribe(String memeDescribe) {
        this.memeDescribe = memeDescribe;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
