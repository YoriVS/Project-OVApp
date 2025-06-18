package org.example.ovapp.traject.bus;

import org.example.ovapp.handler.TimeHandler;
import org.example.ovapp.traject.Stop;

public class Halte extends Stop {
    private String arrivalTime;
    private String departureTime;
    private boolean transferStation;

    public Halte(String uicCode, String name, String arrivalTime, String departureTime, boolean transferStation) {
        super(uicCode, name);
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.transferStation = transferStation;
    }

    public Halte(String uicCode, String name, boolean transferStation) {
        super(uicCode, name);
        this.transferStation = transferStation;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public boolean isTransferStation() {
        return transferStation;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setTransferStation(boolean transferStation) {
        this.transferStation = transferStation;
    }

    @Override
    public String getArrivalInfo() {
        return String.format("%s Arrival Time: %s", super.getName(), TimeHandler.extractHourMinute(arrivalTime));
    }

    @Override
    public String getDepartureInfo() {
        return String.format("%s Departure Time: %s", super.getName(), TimeHandler.extractHourMinute(departureTime));
    }

}
