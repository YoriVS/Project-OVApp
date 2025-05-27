package org.example.ovapp.nsApi.bus;

import org.example.ovapp.traject.Stop;
import org.example.ovapp.traject.Traject;
import org.example.ovapp.traject.bus.Halte;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MappingBusTrajectTest {


    public static ArrayList<Traject> getBusTrajectFromJSONArray(JSONArray trips) {
        ArrayList<Traject> trajects = new ArrayList<>();

        for (Object trip : trips) {
            JSONObject tripJSON = (JSONObject) trip;
            JSONObject beginStation = (JSONObject) tripJSON.get("begin_station");
            JSONObject endStation = (JSONObject) tripJSON.get("end_station");

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

            trajects.add(new Traject(endHalte, startHalte, inBetweenStops.reversed(), startTime, endTime, number, null, totalDuration, inBetweenDuration.reversed(), "Bus"));

        }

        return trajects;
    }
}
