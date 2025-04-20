package org.example.ovapp;

import java.util.ArrayList;
import java.util.List;

public class RouteList {
    static List<Traject> trajects = new ArrayList<>();

    static {
        String[] cities = {
                "Amsterdam", "Utrecht", "Rotterdam", "Den Haag", "Eindhoven",
                "Groningen", "Leeuwarden", "Maastricht", "Arnhem", "Nijmegen",
                "Zwolle", "Enschede", "Breda", "Tilburg", "Haarlem",
                "Delft", "Leiden", "Amersfoort", "Almere", "Apeldoorn"
        };

        int count = 0;
        for (int i = 0; i < cities.length && count < 50; i++) {
            for (int j = 0; j < cities.length && count < 50; j++) {
                if (i != j) {
                    trajects.add(new Traject(cities[i], cities[j]));
                    count++;
                }
            }
        }
    }

    public static List<Traject> getTrajectList() {
        return trajects;
    }
}
