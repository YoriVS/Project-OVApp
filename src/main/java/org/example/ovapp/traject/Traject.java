package org.example.ovapp.traject;

import org.example.ovapp.OVApp;
import org.example.ovapp.handler.TimeHandler;
import org.example.ovapp.traject.train.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Traject {
    Stop from;
    Stop to;
    List<Stop> stops;
    String timeDeparture;
    String timeArrival;
    String number;
    String transferMessages;
    String duration;
    ArrayList<String> inBetweenTrajects;
    String vehicle;
    int inBetweenTrajectsSize = 0;

    public Stop getFrom() {
        return from;
    }

    public void setFrom(Stop from) {
        this.from = from;
    }

    public Stop getTo() {
        return to;
    }

    public void setTo(Stop to) {
        this.to = to;
    }

    public Traject(Stop from, Stop to, List<Stop> stops, String timeDeparture, String timeArrival, String number, String transferMessages, String duration, ArrayList<String> inBetweenTrajects, String vehicle) {
        this.from = from;
        this.to = to;
        this.stops = stops;
        this.timeDeparture = timeDeparture;
        this.timeArrival = timeArrival;
        this.number = number;
        this.transferMessages = transferMessages;
        this.duration = TimeHandler.convertMinuteToHourMinute(duration);
        this.inBetweenTrajects = inBetweenTrajects;
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return TimeHandler.extractHourMinute(timeDeparture) + " --> " + TimeHandler.extractHourMinute(timeArrival) + " Duration:  " + duration;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    /// Return the start station name and end station name
    public String getName() {
        return from.toString() + " --> " + to.toString();
    }

    /// Return the list of all the station in between the start and the end
    public List<Stop> getInBetweenStations() {
        return stops;
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

        switch (vehicle) {
            case "Train":
                for (Stop stop : stops) {
                    Station station = (Station) stop;
                    if (station.getArrivalTime() == null) {
                        trajectInfo.add(station.getDepartureInfo());
                        if (inBetweenTrajectsSize < inBetweenTrajects.size()) {
                            trajectInfo.add(inBetweenTrajects.get(inBetweenTrajectsSize));
                            inBetweenTrajectsSize++;
                        }
                    }
                    else if (station.getDepartureTime() == null) {
                        trajectInfo.add(station.getArrivalInfo());
                        if (!(stops.indexOf(station) == stops.size() - 1)) {
                            trajectInfo.add(OVApp.currentTraject.transferMessages + "\uD83D\uDEB6\u200D♂\uFE0F");
                        }
                    }
                }
                break;

            case "Bus":
                trajectInfo.add(from.getDepartureInfo());
                trajectInfo.add(inBetweenTrajects.getFirst() + "min");
                inBetweenTrajects.removeFirst();
                int i = 0;
                for (Stop stop : stops) {
                    trajectInfo.add(stop.getName());
                    trajectInfo.add(inBetweenTrajects.get(i) + "min");
                    i++;
                }
                trajectInfo.add(inBetweenTrajects.getLast() + "min");
                trajectInfo.add(to.getName());
                break;
        }

        return trajectInfo;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Traject traject = (Traject) o;
        return Objects.equals(number, traject.number);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }

    // Single responsibility principe !!11!!_!!
}
