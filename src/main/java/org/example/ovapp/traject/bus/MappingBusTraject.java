package org.example.ovapp.traject.bus;

import org.example.ovapp.traject.Stop;
import org.example.ovapp.traject.Traject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class MappingBusTraject {

    public static ArrayList<Traject> getBusTrajectFromJSONArray(JSONArray trips, String fromStation, String toStation) {
        ArrayList<Traject> trajects = new ArrayList<>();

        for (Object trip : trips) {
            JSONObject tripJSON = (JSONObject) trip;
            JSONObject beginStation = (JSONObject) tripJSON.get("begin_station");
            JSONObject endStation = (JSONObject) tripJSON.get("end_station");

            if (beginStation.get("name").equals(fromStation) && endStation.get("name").equals(toStation)) {
                Stop startHalte = new Stop((String) beginStation.get("uicCode"), (String) beginStation.get("name"));
                Stop endHalte = new Stop((String) endStation.get("uicCode"), (String) endStation.get("name"));
                String startTime = (String) tripJSON.get("start_time");
                String endTime = (String) tripJSON.get("end_time");
                String number = (String) tripJSON.get("number");
                String totalDuration = ((Long) tripJSON.get("total_time_minutes")).toString();
                ArrayList<Stop> inBetweenStops = new ArrayList<>();
                JSONArray stops = (JSONArray) tripJSON.get("intermediate_stops");

                for (Object stop : stops) {
                    JSONObject stopJSON = (JSONObject) stop;

                    inBetweenStops.add(new Stop((String) stopJSON.get("uicCode"), (String) stopJSON.get("name")));
                }

                trajects.add(new Traject(startHalte, endHalte, inBetweenStops, startTime, endTime, number, null, totalDuration, null));
            }

        }

        return trajects;
    }
}
