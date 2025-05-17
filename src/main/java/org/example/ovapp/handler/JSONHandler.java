package org.example.ovapp.handler;

import org.example.ovapp.Instance;
import org.json.simple.JSONArray;
import java.io.FileReader;

public class JSONHandler {

    /// Load JSON array from file (path)
    public static JSONArray loadJsonArray(String path) {
        try (FileReader reader = new FileReader(path)) {
            return (JSONArray) Instance.parser.parse(reader);
        } catch (Exception e) {
            System.out.println("not working");
            return new JSONArray();
        }
    }
}
