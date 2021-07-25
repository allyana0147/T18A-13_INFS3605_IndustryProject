package com.example.infs3605_industry_project;

import java.util.List;

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

    public static String getLanguage(String language_id, List<Language> languageList) {
        for(final Language language : languageList) {
            if (language.getLanguage_id().equals(language_id)) {
                return language.getLanguage();
            }
        }
        return null;
    }
}
