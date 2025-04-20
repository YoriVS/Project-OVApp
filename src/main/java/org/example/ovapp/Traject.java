package org.example.ovapp;

import java.util.ArrayList;
import java.util.List;

public class Traject {
    Station from;
    Station to;
    List<Station> inBetweenStations;

    Traject(Station from, Station to) {
        this.from = from;
        this.to = to;
        this.inBetweenStations = new ArrayList<>();
    }

    Traject(Station from, Station to, List<Station> inBetweenStation) {
        this.from = from;
        this.to = to;
        this.inBetweenStations = inBetweenStation;
    }

    @Override
    public String toString() {
        return from.toString() + " --> " + to.toString();
    }

    public List<Station> getInBetweenStations() {
        return inBetweenStations;
    }
}
