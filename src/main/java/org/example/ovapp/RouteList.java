package org.example.ovapp;

import java.util.ArrayList;
import java.util.List;

public class RouteList {
    static List<Traject> trajects = new ArrayList<>();

    static {
        Station[] stations = {
                new Station("Amsterdam"), new Station("Utrecht"), new Station("Rotterdam"), new Station("Den Haag"), new Station("Eindhoven"),
                new Station("Groningen"), new Station("Leeuwarden"), new Station("Maastricht"), new Station("Arnhem"), new Station("Nijmegen"),
                new Station("Zwolle"), new Station("Enschede"), new Station("Breda"), new Station("Tilburg"), new Station("Haarlem"),
                new Station("Delft"), new Station("Leiden"), new Station("Amersfoort"), new Station("Almere"), new Station("Apeldoorn")
        };

        int count = 0;
        for (int i = 0; i < stations.length && count < 50; i++) {
            for (int j = 0; j < stations.length && count < 50; j++) {
                if (i != j) {
                    if (i % 2 == 0) {
                        ArrayList<Station> list = new ArrayList<>();
                        list.add(stations[3]);
                        list.add(stations[4]);
                        list.add(stations[7]);
                        list.add(stations[12]);
                        trajects.add(new Traject(stations[i], stations[j], new ArrayList<>(list)));

                    } else {
                        trajects.add(new Traject(stations[i], stations[j]));
                    }
                    count++;
                }
            }
        }
    }

    public static List<Traject> getTrajectList() {
        return trajects;
    }
}
