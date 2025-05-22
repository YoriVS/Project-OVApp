package org.example.ovapp.traject;

import java.time.OffsetDateTime;

public class Route {
    private String startPoint;
    private String endPoint;
    private boolean departureOrNot;
    private String time;
    private String vehicle;

    public Route(String startPoint, String endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Route(String startPoint, String endPoint, boolean departureOrNot, String time, String vehicle) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.departureOrNot = departureOrNot;
        this.time = time;
        this.vehicle = vehicle;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public boolean getDepartureOrNot() {
        return departureOrNot;
    }

    public boolean isDepartureOrNot() {
        return departureOrNot;
    }

    public void setDepartureOrNot(boolean departureOrNot) {
        this.departureOrNot = departureOrNot;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
}
