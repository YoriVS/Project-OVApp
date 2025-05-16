package org.example.ovapp.traject;

import org.example.ovapp.OVApp;
import org.example.ovapp.handler.TimeHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Traject {
    Stop from;
    Stop to;
    List<Stop> stations;
    String timeDeparture;
    String timeArrival;
    String number;
    String transferMessages;
    String duration;
    ArrayList<String> inBetweenTrajects;
    int inBetweenTrajectsSize = 0;

    public Traject(Stop from, Stop to, List<Stop> station, String timeDeparture, String timeArrival, String number, String transferMessages, String duration, ArrayList<String> inBetweenTrajects) {
        this.from = from;
        this.to = to;
        this.stations = station;
        this.timeDeparture = timeDeparture;
        this.timeArrival = timeArrival;
        this.number = number;
        this.transferMessages = transferMessages;
        this.duration = TimeHandler.convertHourToHourMinute(duration);
        this.inBetweenTrajects = inBetweenTrajects;
    }

    @Override
    public String toString() {
        return TimeHandler.extractHourMinute(timeDeparture) + " --> " + TimeHandler.extractHourMinute(timeArrival) + " Duration:  " + duration;
    }

    /// Return the start station name and end station name
    public String getName() {
        return from.toString() + " --> " + to.toString();
    }

    /// Return the list of all the station in between the start and the end
    public List<Stop> getInBetweenStations() {
        return stations;
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

        for (Stop stop : stations) {
            Station station = (Station) stop;
            if (station.arrivalTime == null) {
                trajectInfo.add(station.getDepartureInfo());
                if (inBetweenTrajectsSize < inBetweenTrajects.size()) {
                    trajectInfo.add(inBetweenTrajects.get(inBetweenTrajectsSize));
                    inBetweenTrajectsSize++;
                }
            }
            else if (station.departureTime == null) {
                trajectInfo.add(station.getArrivalInfo());
                if (!(stations.indexOf(station) == stations.size() - 1)) {
                    trajectInfo.add(OVApp.currentTraject.transferMessages + "\uD83D\uDEB6\u200D♂\uFE0F");
                }
            }
        }
        return trajectInfo;
    }
}
