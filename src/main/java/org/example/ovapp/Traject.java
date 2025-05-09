package org.example.ovapp;

import org.example.ovapp.controller.MenuController;
import org.example.ovapp.handler.TimeHandler;

import java.util.ArrayList;
import java.util.List;

public class Traject {
    Station from;
    Station to;
    List<InBetweenStation> inBetweenStations;
    String timeDeparture;
    String timeArrival;
    String number;
    String transferMessages;
    String duration;
    ArrayList<String> inBetweenTrajects;
    int inBetweenTrajectsSize = 0;

    public Traject(Station from, Station to, List<InBetweenStation> inBetweenStation, String timeDeparture, String timeArrival, String number, String transferMessages, String duration, ArrayList<String> inBetweenTrajects) {
        int tempIntDuration = Integer.parseInt(duration);
        this.from = from;
        this.to = to;
        this.inBetweenStations = inBetweenStation;
        this.timeDeparture = timeDeparture;
        this.timeArrival = timeArrival;
        this.number = number;
        this.transferMessages = transferMessages;
        this.duration = (tempIntDuration / 60) >= 0 ? String.valueOf(tempIntDuration / 60) : duration;
        this.inBetweenTrajects = inBetweenTrajects;
    }

    @Override
    public String toString() {
        return TimeHandler.extractHourMinute(timeDeparture) + " --> " + TimeHandler.extractHourMinute(timeArrival) + "duration:  " + duration;
    }

    /// Return the start station name and end station name
    public String getName() {
        return from.toString() + " --> " + to.toString();
    }

    /// Return the list of all the station in between the start and the end
    public List<InBetweenStation> getInBetweenStations() {
        return inBetweenStations;
    }

    /// Return the departure time
    public String getDepartureTime() {
        return timeDeparture;
    }

    /// Return the arrival time
    public String getArrivalTime() {
        return timeArrival;
    }

    /// Return the traject info
    public ArrayList<String> getTrajectInfo() {
        ArrayList<String> trajectInfo = new ArrayList<>();

        for (InBetweenStation station : inBetweenStations) {
            if (station.arrivalTime == null) {
                trajectInfo.add(station.getDepartureInfo());
                if (inBetweenTrajectsSize < inBetweenTrajects.size()) {
                    trajectInfo.add(inBetweenTrajects.get(inBetweenTrajectsSize));
                    inBetweenTrajectsSize++;
                }
            }
            else if (station.departureTime == null) {
                trajectInfo.add(station.getArrivalInfo());
                if (!(inBetweenStations.indexOf(station) == inBetweenStations.size() - 1)) {
                    trajectInfo.add(OVApp.currentTraject.transferMessages);
                }
            }

        }
        return trajectInfo;
    }
}
