package org.example.ovapp.nsApi.train;

import org.example.ovapp.Instance;
import org.example.ovapp.handler.JSONHandler;
import org.example.ovapp.traject.bus.MappingBusTraject;
import org.example.ovapp.traject.train.MappingTrainTraject;
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

    public static ArrayList<Traject> getTrips(String fromStop, String toStop, String dateTime, boolean departureOrArrival, String vehicle) {
        ArrayList<Traject> trajects = new ArrayList<>();
        try {
            String encodedFrom = URLEncoder.encode(fromStop, StandardCharsets.UTF_8);
            String encodedTo = URLEncoder.encode(toStop, StandardCharsets.UTF_8);

            OffsetDateTime baseDateTime = OffsetDateTime.parse(dateTime);

            String formattedDateTime = URLEncoder.encode(baseDateTime.toString(), StandardCharsets.UTF_8);

            String urlStr = String.format(Instance.urlTips, encodedFrom, encodedTo, formattedDateTime, departureOrArrival);

            HttpURLConnection connection = NSApiSecurity.getConnection(urlStr);

            switch (vehicle) {
                case "Train":
                    JSONObject trainTripsJSON = (JSONObject) new JSONParser().parse(new InputStreamReader(connection.getInputStream()));
                    JSONArray trainTrips = (JSONArray) trainTripsJSON.get("trips");
                    trajects.addAll((MappingTrainTraject.getTrainTrajectFromJSONArray(trainTrips, encodedFrom, encodedTo)));
                    break;

                case "Bus":
                    JSONArray busTrips = JSONHandler.loadJsonArray("/JSON/busTraject.json");
                    trajects.addAll(MappingBusTraject.getBusTrajectFromJSONArray(busTrips, fromStop, toStop));
                    break;
            }

            return trajects;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return trajects;
    }
}
