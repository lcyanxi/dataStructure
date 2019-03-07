package com.douguo.ndc.model;

public class LiveClass {
    private int classId;
    private int userId;
    private String title;
    private String nickname;
    private int sales;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        return "LiveClass{" +
                "classId=" + classId +
                ", userId=" + userId +
                ", title=" + title +
                ", nickname='" + nickname + '\'' +
                ", sales=" + sales +
                '}';
    }
}
