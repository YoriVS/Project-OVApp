package org.example.ovapp.traject.train;

import org.example.ovapp.traject.Stop;
import org.example.ovapp.traject.Traject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappingTrainTraject {


    public static ArrayList<Traject> getTrainTrajectFromJSONArray(JSONArray trips, String encodedFrom, String encodedTo) {
        ArrayList<Traject> trajects = new ArrayList<>();

        for (Object tripObj : trips) {
            JSONObject trip = (JSONObject) tripObj;
            String input = trip.get("uid").toString();

            Map<String, String> values = new HashMap<>();
            for (String part : input.split("\\|")) {
                if (part.contains("=")) {
                    String[] keyValue = part.split("=", 2);
                    values.put(keyValue[0], keyValue[1]);
                }
            }
            Stop from = new Stop(values.get("fromStation"), encodedFrom.replace("+", " "));
            Stop to = new Stop(values.get("toStation"), encodedTo.replace("+", " "));
            String timeDeparture = values.get("plannedFromTime");
            String timeArrival = values.get("plannedArrivalTime");
            String number = values.get("tripSummaryHash");
            String totalDuration = ((Long) trip.get("plannedDurationInMinutes")).toString();
            String transferMessage = null;
            ArrayList<String> inBetweenTrajects = new ArrayList<>();

            List<Stop> stations = new ArrayList<>();
            JSONArray legs = (JSONArray) trip.get("legs");
            for (Object leg: legs) {
                JSONObject legJSON = (JSONObject) leg;
                JSONArray stopJSON = (JSONArray)  legJSON.get("stops");
                JSONArray transferMessagesJSON = (JSONArray) legJSON.get("transferMessages");
                if (legJSON.get("duration") != null) {
                    JSONObject durationJSON = (JSONObject) legJSON.get("duration");
                    inBetweenTrajects.add(durationJSON.get("accessibilityValue") + " \uD83D\uDE86");
                }
                if (transferMessagesJSON != null) {
                    JSONObject transferMessageJSON = (JSONObject) transferMessagesJSON.getFirst();
                    transferMessage = transferMessageJSON.get("message").toString();
                }

                for (Object stopObj : stopJSON) {
                    JSONObject stop = (JSONObject) stopObj;
                    stations.add(new Station(
                            (String) stop.get("uicCode"),
                            (String) stop.get("name"),
                            (String) stop.get("plannedArrivalDateTime"),
                            (String) stop.get("plannedDepartureDateTime"),
                            (String) stop.get("plannedArrivalTrack"),
                            (String) stop.get("plannedDepartureTrack"),
                            (stop.get("actualDepartureDateTime") == null)));

                }
            }
            Traject traject = new Traject(from, to, stations, timeDeparture, timeArrival, number, transferMessage, totalDuration, inBetweenTrajects);
            trajects.add(traject);
        }
        return trajects;
    }
}
