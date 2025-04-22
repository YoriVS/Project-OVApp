package org.example.ovapp;

public class LanguageHandler {

    public enum Language {EN, NL};
    private Language currentLanguage;


    public void setLanguage(Language lang) {
        currentLanguage = lang;
    }
    public Language getLanguage() {
        return currentLanguage;
    }
}
