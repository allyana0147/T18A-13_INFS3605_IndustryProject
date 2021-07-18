package com.example.infs3605_industry_project;

public class Comment {

    public String post_id, user_name, location, comment;

    public Comment(){

    }

    public Comment(String post_id, String user_name, String location, String comment){
        this.post_id = post_id;
        this.user_name = user_name;
        this.location = location;
        this.comment = comment;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
