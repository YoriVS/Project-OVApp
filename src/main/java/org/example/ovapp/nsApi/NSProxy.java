package org.example.ovapp.nsApi;

import org.example.ovapp.Instance;
import org.example.ovapp.handler.MappingTraject;
import org.example.ovapp.traject.Traject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.ArrayList;

public class NSProxy {

    public static ArrayList<Traject> getTrips(String fromStation, String toStation, String dateTime, boolean departureOrArrival) {
        ArrayList<Traject> trajects = new ArrayList<>();
        try {
            String encodedFrom = URLEncoder.encode(fromStation, StandardCharsets.UTF_8);
            String encodedTo = URLEncoder.encode(toStation, StandardCharsets.UTF_8);

            OffsetDateTime baseDateTime = OffsetDateTime.parse(dateTime);

            String formattedDateTime = URLEncoder.encode(baseDateTime.toString(), StandardCharsets.UTF_8);

            String urlStr = String.format(Instance.urlTips, encodedFrom, encodedTo, formattedDateTime, departureOrArrival);

            HttpURLConnection connection = NSApiSecurity.getConnection(urlStr);
            JSONObject tripsJSON = (JSONObject) new JSONParser().parse(new InputStreamReader(connection.getInputStream()));
            JSONArray trips = (JSONArray) tripsJSON.get("trips");

            trajects.addAll((MappingTraject.getTrajectFromJSONArray(trips, encodedFrom, encodedTo)));

            return trajects;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return trajects;
    }
}
