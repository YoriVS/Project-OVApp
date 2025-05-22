package org.example.ovapp.handler;

import org.example.ovapp.Instance;
import org.example.ovapp.OVApp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class JSONHandler {

    /// Load JSON array from file (path)
    public static JSONObject loadJsonObject(String resourcePath) {
        InputStream inputStream = OVApp.class.getResourceAsStream(resourcePath);
        try {
            assert inputStream != null;
            try (Reader reader = new InputStreamReader(inputStream)) {
                return (JSONObject) Instance.parser.parse(reader);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Could not parse JSON from resource '" + resourcePath + "'. Details: " + e.getMessage());
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public static JSONArray loadJsonArray(String resourcePath) {
        InputStream inputStream = OVApp.class.getResourceAsStream(resourcePath);
        try {
            assert inputStream != null;
            try (Reader reader = new InputStreamReader(inputStream)) {
                return (JSONArray) Instance.parser.parse(reader);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Could not parse JSON from resource '" + resourcePath + "'. Details: " + e.getMessage());
            e.printStackTrace();
            return new JSONArray();
        }
    }
}
