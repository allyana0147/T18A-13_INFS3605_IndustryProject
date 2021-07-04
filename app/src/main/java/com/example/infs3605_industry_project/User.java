package com.example.infs3605_industry_project;

import java.util.List;
public class User {

    public String email, password, name, user_type, location, language;

    public User(){

    }

    public User(String email, String password, String name, String user_type, String location, String language){
        this.email = email;
        this.password = password;
        this.name = name;
        this.user_type = user_type;
        this.location = location;
        this.language = language;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public static User getUser(String email, List<User> userList) {
        for(final User user : userList) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

}


