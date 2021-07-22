package com.example.infs3605_industry_project;

import java.util.List;

public class Profile {

    public String profile_id, user_id, user_name,location, no_of_posts, no_of_followers, total_points, no_of_points, no_rewards_redeemed;

    public Profile(){

    }

    public Profile(String profile_id, String user_id, String user_name, String location, String no_of_posts, String no_of_followers, String total_points, String no_of_points, String no_rewards_redeemed) {
        this.profile_id = profile_id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.location = location;
        this.no_of_posts = no_of_posts;
        this.no_of_followers = no_of_followers;
        this.total_points = total_points;
        this.no_of_points = no_of_points;
        this.no_rewards_redeemed = no_rewards_redeemed;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getNo_of_posts() {
        return no_of_posts;
    }

    public void setNo_of_posts(String no_of_posts) {
        this.no_of_posts = no_of_posts;
    }

    public String getNo_of_followers() {
        return no_of_followers;
    }

    public void setNo_of_followers(String no_of_followers) {
        this.no_of_followers = no_of_followers;
    }

    public String getTotal_points() {
        return total_points;
    }

    public void setTotal_points(String total_points) {
        this.total_points = total_points;
    }

    public String getNo_of_points() {
        return no_of_points;
    }

    public void setNo_of_points(String no_of_points) {
        this.no_of_points = no_of_points;
    }

    public String getNo_rewards_redeemed() {
        return no_rewards_redeemed;
    }

    public void setNo_rewards_redeemed(String no_rewards_redeemed) {
        this.no_rewards_redeemed = no_rewards_redeemed;
    }

    public static Profile getProfile(String id, List<Profile> profileList) {
        for(final Profile profile : profileList) {
            if (profile.getUser_id().equals(id)) {
                return profile;
            }
        }
        return null;
    }


}
