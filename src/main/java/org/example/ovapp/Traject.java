package org.example.ovapp;

import java.util.ArrayList;
import java.util.List;

public class Traject {
    Station from;
    Station to;
    List<Station> inBetweenStations;
    String timeDeparture;
    String timeArrival;
    int number;

    public Traject(Station from, Station to) {
        this.from = from;
        this.to = to;
        this.inBetweenStations = new ArrayList<>();
    }

    public Traject(Station from, Station to, List<Station> inBetweenStation, String timeDeparture, String timeArrival, int number) {
        this.from = from;
        this.to = to;
        this.inBetweenStations = inBetweenStation;
        this.timeDeparture = timeDeparture;
        this.timeArrival = timeArrival;
        this.number = number;
    }

    @Override
    public String toString() {
        return timeDeparture + " --> " + timeArrival;
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
