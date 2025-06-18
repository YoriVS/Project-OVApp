package org.example.ovapp.handler;

import org.example.ovapp.Instance;
import org.example.ovapp.OVApp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;

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

    public static void saveJsonArray(String path, JSONArray array) {
        path = "src/main/resources/" + path;
        try (FileWriter file = new FileWriter(path)) {
            file.write(array.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
