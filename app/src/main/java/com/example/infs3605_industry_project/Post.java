package com.example.infs3605_industry_project;

import java.util.ArrayList;

public class Post {

    public String post_id, user_name, post, location, caption;

    public Post(){

    }

    public Post(String post_id, String user_name, String post, String location, String caption){
        this.post_id = post_id;
        this.user_name = user_name;
        this.post = post;
        this.location = location;
        this.caption = caption;


    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }


    //creates top 10 movies objects stored in array list called 'movies'
    public static ArrayList<Post> getPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post("post1", "John Smith", "SAMPLE POST", "Community", "This is the sun in our community"));
        posts.add(new Post("post2", "Helen Jones", "SAMPLE POST", "Community", "This is the sky in our community"));
        posts.add(new Post("post3", "Abigail Breslin", "SAMPLE POST", "Community", "This is the water in our community"));
        return posts;
    }

    //returns a movie based on id
    public static Post getPosts(String id) {
        ArrayList<Post> posts = Post.getPosts();
        for(final Post post : posts) {
            if (post.getPost_id().equals(id)) {
                return post;
            }
        }
        return null;
    }

}
