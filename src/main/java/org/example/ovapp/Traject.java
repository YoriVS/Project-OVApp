package org.example.ovapp;

import org.example.ovapp.controller.MenuController;

import java.util.List;

public class Traject {
    Station from;
    Station to;
    List<Station> inBetweenStations;
    String timeDeparture;
    String timeArrival;
    String number;

    public Traject(Station from, Station to, List<Station> inBetweenStation, String timeDeparture, String timeArrival, String number) {
        this.from = from;
        this.to = to;
        this.inBetweenStations = inBetweenStation;
        this.timeDeparture = timeDeparture;
        this.timeArrival = timeArrival;
        this.number = number;
    }

    @Override
    public String toString() {
        return MenuController.extractHourMinute(timeDeparture) + " --> " + MenuController.extractHourMinute(timeArrival);
    }

    public String getName() {
        return from.toString() + " --> " + to.toString();
    }

    public List<Station> getInBetweenStations() {
        return inBetweenStations;
    }

    public String getDepartureTime() {
        return timeDeparture;
    }

    public String getArrivalTime() {
        return timeArrival;
    }
}
