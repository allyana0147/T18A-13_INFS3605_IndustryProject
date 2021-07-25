package com.example.infs3605_industry_project;

public class Language {

    public String language_id, language;

    public Language(){

    }

    public Language(String language_id, String language) {
        this.language_id = language_id;
        this.language = language;
    }

    public String getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(String language_id) {
        this.language_id = language_id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
