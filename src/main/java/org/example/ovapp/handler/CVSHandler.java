package org.example.ovapp.handler;
import com.opencsv.CSVReader;
import org.json.simple.JSONArray;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class CVSHandler {
    public static void getJSONFromCVS(String filename) {
        JSONArray jsonArray = new JSONArray();

        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            List<String[]> line = reader.readAll();
            System.out.println(line);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
