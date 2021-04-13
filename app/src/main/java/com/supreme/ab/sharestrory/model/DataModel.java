package com.supreme.ab.sharestrory.model;

public class DataModel {
    private String text;
    private String img;
    private String link;
    private int viewCount;

    public DataModel(String text, String img, String link) {
        this.text = text;
        this.img = img;
        this.link = link;
        viewCount=0;
    }
    public DataModel(){

    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return img;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
