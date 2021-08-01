package com.example.infs3605_industry_project;

import java.util.ArrayList;
import java.util.List;

public class LikedPost {
    public String liked_post_id, user_name, post_id;

    public LikedPost(){

    }

    public LikedPost(String liked_post_id, String user_name, String post_id) {
        this.liked_post_id = liked_post_id;
        this.user_name = user_name;
        this.post_id = post_id;
    }

    public String getLiked_post_id() {
        return liked_post_id;
    }

    public void setLiked_post_id(String liked_post_id) {
        this.liked_post_id = liked_post_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public static List<LikedPost> getLikedPostBasedOnUser (String user_name, List<LikedPost> likedPostList) {
        List<LikedPost> likedPostListBasedOnUser = new ArrayList<>();
        for(final LikedPost likedPost : likedPostList) {
            if (likedPost.getUser_name().equals(user_name)) {
                likedPostListBasedOnUser.add(likedPost);
            }
        }
        return likedPostListBasedOnUser;
    }

    public static String getLikedPostId(String post_id, String user_name, List<LikedPost> likedPostList) {
        for(final LikedPost likedPost : likedPostList) {
            if (likedPost.getPost_id().equals(post_id) && likedPost.getUser_name().equals(user_name)) {
                return likedPost.getLiked_post_id();
            }
        }
        return null;
    }
}

