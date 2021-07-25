package com.example.infs3605_industry_project;

import java.util.ArrayList;

public class Post {

    public String post_id, imageUrl, user_name, name, location, caption, hashtag, type;

    public Post(){

    }

    public Post(String post_id, String imageUrl, String user_name, String name, String location, String caption, String hashtag, String type){
        this.post_id = post_id;
        this.imageUrl = imageUrl;
        this.user_name = user_name;
        this.name = name;
        this.location = location;
        this.caption = caption;
        this.hashtag = hashtag;
        this.type = type;

    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
