package com.example.infs3605_industry_project;

public class Model {

    private String imageUrl, user_name;

    public Model(){
    }

    public Model(String imageUrl, String user_name) {
        this.imageUrl = imageUrl;
        this.user_name = user_name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
