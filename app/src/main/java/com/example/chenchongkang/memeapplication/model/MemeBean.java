package com.example.chenchongkang.memeapplication.model;

/**
 * Created by chenchongkang on 2018/4/17.
 */

public class MemeBean {
    private Integer memeID;
    private String src;
    private String memeName;
    private Integer downloads;
    private String memeIntro;
    private String upTime;
    private String classis;//classes 貌似会出错
    private String author;
    private String cover;

    public Integer getMemeID() {
        return memeID;
    }

    public void setMemeID(Integer memeID) {
        this.memeID = memeID;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getMemeName() {
        return memeName;
    }

    public void setMemeName(String memeName) {
        this.memeName = memeName;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public String getMemeIntro() {
        return memeIntro;
    }

    public void setMemeIntro(String memeIntro) {
        this.memeIntro = memeIntro;
    }

    public String getUpTime() {
        String str = upTime.toString();
        str = str.substring(0, str.length() - 2);
        return str;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public String getClassis() {
        return classis;
    }

    public void setClassis(String classis) {
        this.classis = classis;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
