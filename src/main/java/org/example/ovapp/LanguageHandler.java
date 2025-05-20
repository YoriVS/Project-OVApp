package org.example.ovapp;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import java.io.FileReader;

public class LanguageHandler {

    //some variables to help with language handling
    public enum Language {EN, NL}
    private Language currentLanguage = Language.EN;
    //current language chosen in the settings menu
    private Map<String, String> currentText;
    //default language to fall back on (english)
    private Map<String, String> defaultText;


    public void setLanguage(Language lang) {
        currentLanguage = lang;
        loadLanguage();
    }
    public Language getLanguage() {
        return currentLanguage;
    }
    public String get(String key) {
        return currentText.getOrDefault(key, defaultText.getOrDefault(key, "??" + key + "??"));
    }

    private void loadLanguage() {
        String lang = currentLanguage.name();
        currentText = loadFromFile("resources/JSON/lang_" + lang + ".json");
        defaultText = loadFromFile("resources/JSON/lang_EN.json");
    }   //reloads the language files
    private static Map<String, String> loadFromFile(String path) {
        Map<String, String> map = new HashMap<>();
        try {
            JSONObject obj = (JSONObject) Instance.parser.parse(new FileReader(path));
            for (Object key : obj.keySet()) {
                map.put(key.toString(), obj.get(key).toString());
            }
        } catch (Exception e) {
            System.out.println("Language file load Error: " + path);
        }
        return map;
    }   //loads a language file
}
