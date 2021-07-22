package com.example.infs3605_industry_project;

import java.util.ArrayList;
import java.util.List;

public class Comment {

    public String comment_id, post_id, user_name, location, comment;

    public Comment(){

    }

    public Comment(String comment_id, String post_id, String user_name, String location, String comment){
        this.comment_id = comment_id;
        this.post_id = post_id;
        this.user_name = user_name;
        this.location = location;
        this.comment = comment;

    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
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

    public static List<Comment> getCommentListBasedOnPost(String post_id, List<Comment> commentList) {
        List<Comment> commentListBasedOnPost = new ArrayList<>();
        for(final Comment comment : commentList) {
            if (comment.getPost_id().equals(post_id)) {
                commentListBasedOnPost.add(comment);
            }
        }
        return commentListBasedOnPost;
    }

}
