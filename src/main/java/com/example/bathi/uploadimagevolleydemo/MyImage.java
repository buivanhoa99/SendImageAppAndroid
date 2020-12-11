package com.example.bathi.uploadimagevolleydemo;

public class MyImage {
    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public MyImage(String link, String user, String time, String description) {
        Link = link;
        User = user;
        Time = time;
        Description = description;
    }

    private String Link;
    private String User;
    private String Time;
    private String Description;

}
