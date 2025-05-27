package org.example.ovapp.nsApi.bus;

import org.example.ovapp.handler.JSONHandler;
import org.example.ovapp.traject.Stop;
import org.example.ovapp.traject.Traject;
import org.json.simple.JSONArray;

import javax.crypto.spec.PSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Test {

    public static void main(String[] args) {
        JSONArray busTrips = JSONHandler.loadJsonArray("/JSON/busTraject.json");
        ArrayList<Traject> trajects = MappingBusTrajectTest.getBusTrajectFromJSONArray(busTrips);

        searchTraject(trajects, "Utrecht, CS Centrumzijde", "Amersfoort, Centraal Station");
        searchTraject(trajects, "Utrecht, UMC Hoofdingang", "Amersfoort, De Kei");
        searchTraject(trajects, "Utrecht, Neude", "Amersfoort, De Kei");
        searchTraject(trajects, "Utrecht, CS Jaarbeurszijde", "Amsterdam, Centraal Station");
        searchTraject(trajects, "Utrecht, Vaartsche Rijn", "Amsterdam, Amstelstation");
        searchTraject(trajects, "Amersfoort, De Kei", "Amsterdam, Centraal Station");
        searchTraject(trajects, "Utrecht, CS Centrumzijde", "Amsterdam, Station Zuid");

    }

    private static void searchTraject(ArrayList<Traject> trajects, String begin, String end) {
        ArrayList<ArrayList<String>> allTrajects = new ArrayList<>();

        for (Traject traject: trajects) {
            allTrajects.add(traject.getFullTraject());
        }

        Map<Integer, ArrayList<String>> allStops = new HashMap<>();


        ArrayList<Integer> allTrajectNumber = new ArrayList<>();
        for (int i = 0; i < allTrajects.size(); i++) {
            allTrajectNumber.add(i);
        }

        for (ArrayList<String> traject: allTrajects) {
            allStops.put(allTrajectNumber.get(allTrajects.indexOf(traject)), traject);
        }

        System.out.print("Searching for trajects: ");

        System.out.println(begin + " --> " + end);

        String inBetweenStop = "";

        ArrayList<ArrayList<String>> trajectWithBegin = new ArrayList<>();
        ArrayList<ArrayList<String>> trajectWithEnd = new ArrayList<>();

        for (ArrayList<String> allTraject : allTrajects) {
            boolean tempBeginFound = false;
            boolean tempEndFound = false;
            for (String stop : allTraject) {
                if (stop.equals(begin)) {
                    tempBeginFound = true;
                } else if (stop.equals(end)) {
                    tempEndFound = true;
                } else if (tempBeginFound && tempEndFound) {
                    break;
                }
            }

            if (tempBeginFound && tempEndFound) {
                System.out.format("Traject is : %s --> %s\n", begin, end);
                break;
            }
            if (tempBeginFound) {
                if (!allTraject.getLast().equals(begin)) trajectWithBegin.add(allTraject);
            } else if (tempEndFound) {
                if (!allTraject.getFirst().equals(end)) trajectWithEnd.add(allTraject);
            }

            for (ArrayList<String> startTrajects : trajectWithBegin) {
                for (ArrayList<String> endTrajects : trajectWithEnd) {
                    ArrayList<String> tempStartTraject = startTrajects;
                    ArrayList<String> tempEndTraject = endTrajects;
                    if (startTrajects.size() > endTrajects.size()) {
                        tempStartTraject = endTrajects;
                        tempEndTraject = startTrajects;
                    }
                    for (String stopStart: tempStartTraject) {
                        for (String stopEnd: tempEndTraject) {
                            if (stopStart.equals(stopEnd)) {
                                inBetweenStop = stopStart;
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (!Objects.equals(begin, "") && !Objects.equals(inBetweenStop, "") && !Objects.equals(end, "")) {
            System.out.format("Traject is: %s --> %s --> %s\n\n", begin, inBetweenStop, end);
        } else {
            System.out.println("Tajects not found\n");
        }
    }
}
