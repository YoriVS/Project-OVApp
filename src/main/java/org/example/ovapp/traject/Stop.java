package org.example.ovapp.traject;

import org.example.ovapp.handler.TimeHandler;

public class Stop {
    String arrivalTime;
    String departureTime;
    String name;
    String uicCode;

    public Stop(String uicCode, String name) {
        this.name = name;
        this.uicCode = uicCode;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getUicCode() {
        return uicCode;
    }


    public String getArrivalInfo() {
        return String.format("%s Arrival Time: %s", name, TimeHandler.extractHourMinute(arrivalTime));
    }
    public String getDepartureInfo() {
        return String.format("%s Departure Time: %s", name, TimeHandler.extractHourMinute(departureTime));
    }
}
