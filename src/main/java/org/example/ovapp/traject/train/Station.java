package org.example.ovapp.traject.train;

import org.example.ovapp.handler.TimeHandler;
import org.example.ovapp.traject.Stop;

public class Station extends Stop {
    private String arrivalTime;
    private String departureTime;
    private String arrivalTrack;
    private String departureTrack;
    private boolean transferStation;

    public Station(String uicCode, String name, String arrivalTime, String departureTime, String arrivalTrack, String departureTrack, boolean transferStation) {
        super(uicCode, name);
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.departureTrack = departureTrack;
        this.arrivalTrack = arrivalTrack;
        this.transferStation = transferStation;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public boolean arrivalTimeExiste() {
        return arrivalTime != null;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTrack() {
        return arrivalTrack;
    }

    public void setArrivalTrack(String arrivalTrack) {
        this.arrivalTrack = arrivalTrack;
    }

    public String getDepartureTrack() {
        return departureTrack;
    }

    public void setDepartureTrack(String departureTrack) {
        this.departureTrack = departureTrack;
    }

    public boolean isTransferStation() {
        return transferStation;
    }

    public void setTransferStation(boolean transferStation) {
        this.transferStation = transferStation;
    }

    @Override
    public String getArrivalInfo() {
        String info;
        if (arrivalTrack == null) {
            info = String.format("%s Arrival Time: %s", super.getName(), TimeHandler.extractHourMinute(arrivalTime));
        } else {
            info = String.format("%s Arrival Time: %s Track %s", super.getName(), TimeHandler.extractHourMinute(arrivalTime), arrivalTrack);
        }
        return info;
    }
    @Override
    public String getDepartureInfo() {
        String info;
        if (arrivalTrack == null) {
            info = String.format("%s Departure Time: %s", super.getName(), TimeHandler.extractHourMinute(departureTime));
        } else {
            info = String.format("%s Departure Time: %s Track %s", super.getName(), TimeHandler.extractHourMinute(departureTime), departureTrack);
        }
        return info;
    }

    public String getInfo() {
        return String.format("uicCode: %s \nname: %s \narrivalTime: %s\n departureInfo: %s \ndepartureTrack: %s \narrivalTrack: %s \ntransferStation: %s", super.getUicCode(), super.getName(), arrivalTime, departureTrack, departureTrack, arrivalTrack, transferStation);
    }

}
