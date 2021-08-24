package com.example.deliveryshopping;

public class Review {

    private String userName;
    private String text;
    private String date;

    public Review(String userName, String text, String date) {
        this.userName = userName;
        this.text = text;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
