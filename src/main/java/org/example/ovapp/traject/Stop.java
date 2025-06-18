package org.example.ovapp.traject;

import org.example.ovapp.handler.TimeHandler;

import java.util.Objects;

public class Stop {
    private String arrivalTime;
    private String departureTime;
    private String name;
    private String uicCode;

    public Stop(String uicCode, String name) {
        this.name = name;
        this.uicCode = uicCode;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(this.getName(), o.toString());
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

    public String getArrivalTime() {
        return arrivalTime;
    }
}
