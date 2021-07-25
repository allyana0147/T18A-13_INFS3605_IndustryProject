package com.example.infs3605_industry_project;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {

    public String dictionary_id, language, translation, word;

    public Dictionary(){

    }

    public Dictionary(String dictionary_id, String language, String translation, String word) {
        this.dictionary_id = dictionary_id;
        this.language = language;
        this.translation = translation;
        this.word = word;
    }

    public String getDictionary_id() {
        return dictionary_id;
    }

    public void setDictionary_id(String dictionary_id) {
        this.dictionary_id = dictionary_id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public static List<Dictionary> getDictionaryBasedOnLanguage(String language, List<Dictionary> dictionaryList) {
        List<Dictionary> dictionaryListBasedOnLanguage = new ArrayList<>();
        for(final Dictionary dictionary : dictionaryList) {
            if (dictionary.getLanguage().equals(language)) {
                dictionaryListBasedOnLanguage.add(dictionary);
            }
        }
        return dictionaryListBasedOnLanguage;
    }
}
