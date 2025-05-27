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
                String startTime = (String) tripJSON.get("start_time");
                String endTime = (String) tripJSON.get("end_time");
                Halte startHalte = new Halte((String) beginStation.get("uicCode"), (String) beginStation.get("name"), null, startTime, false);
                Halte endHalte = new Halte((String) endStation.get("uicCode"), (String) endStation.get("name"), endTime, null, false);
                String number = (String) tripJSON.get("number");
                String totalDuration = ((Long) tripJSON.get("total_time_minutes")).toString();
                JSONArray legDurationsMinutes = (JSONArray) tripJSON.get("leg_durations_minutes");
                JSONArray stops = (JSONArray) tripJSON.get("intermediate_stops");
                ArrayList<Stop> inBetweenStops = new ArrayList<>();
                ArrayList<String> inBetweenDuration = new ArrayList<>();

                for (Object stop : stops) {
                    JSONObject stopJSON = (JSONObject) stop;
                    inBetweenStops.add(new Stop((String) stopJSON.get("uicCode"), (String) stopJSON.get("name")));
                }

                for (Object time : legDurationsMinutes) {
                    Long timeLong = (Long) time;
                    inBetweenDuration.add(timeLong.toString());
                }

                trajects.add(new Traject(startHalte, endHalte, inBetweenStops, startTime, endTime, number, null, totalDuration, inBetweenDuration, "Bus"));
            }

        }

        return trajects;
    }
}
