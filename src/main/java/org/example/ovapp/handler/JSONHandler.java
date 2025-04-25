package org.example.ovapp.handler;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JSONHandler {
    private static final JSONParser parser = new JSONParser();

    public static JSONArray loadJsonArray(String path) {
        try (FileReader reader = new FileReader(path)) {
            return (JSONArray) parser.parse(reader);
        } catch (Exception e) {
            System.out.println("not working");
            return new JSONArray();
        }
    }
}
