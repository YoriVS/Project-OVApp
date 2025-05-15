package org.example.ovapp.traject;

import org.example.ovapp.handler.TimeHandler;

public class Station extends Stop {
    String arrivalTime;
    String departureTime;
    String arrivalTrack;
    String departureTrack;
    boolean transferStation;

    public Station(String uicCode, String name, String arrivalTime, String departureTime, String arrivalTrack, String departureTrack, boolean transferStation) {
        super(uicCode, name);
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.departureTrack = departureTrack;
        this.arrivalTrack = arrivalTrack;
        this.transferStation = transferStation;
    }

    public String getArrivalInfo() {
        return String.format("%s Arrival Time: %s Track %s", name, TimeHandler.extractHourMinute(arrivalTime), arrivalTrack);
    }
    public String getDepartureInfo() {
        return String.format("%s Departure Time: %s Track %s", name, TimeHandler.extractHourMinute(departureTime), departureTrack);
    }

}
