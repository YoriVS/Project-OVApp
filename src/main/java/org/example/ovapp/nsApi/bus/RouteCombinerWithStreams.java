package org.example.ovapp.nsApi.bus;

import org.example.ovapp.handler.JSONHandler;
import org.example.ovapp.traject.Traject;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class RouteCombinerWithStreams {

    public static ArrayList<ArrayList<String>> findRoutesWithOneTransfer(
            String overallBegin,
            String overallEnd,
            List<ArrayList<String>> allTrajects) {

        List<ArrayList<String>> trajectsStartingWithBegin = allTrajects.stream()
                .filter(traject -> traject.contains(overallBegin))
                .map(traject -> {
                    int beginIndex = traject.indexOf(overallBegin);
                    return new ArrayList<>(traject.subList(beginIndex, traject.size()));
                })
                .filter(traject -> traject.size() > 1)
                .toList();

        List<ArrayList<String>> trajectsEndingWithEnd = allTrajects.stream()
                .filter(traject -> traject.contains(overallEnd))
                .map(traject -> {
                    int endIndex = traject.indexOf(overallEnd);
                    return new ArrayList<>(traject.subList(0, endIndex + 1));
                })
                .filter(traject -> traject.size() > 1)
                .toList();

        System.out.println("Begin: ");
        trajectsStartingWithBegin.forEach(System.out::println);
        System.out.println();
        System.out.println("End: ");
        trajectsEndingWithEnd.forEach(System.out::println);
        System.out.println();

        List<CombinedLeg> foundRoutes = trajectsStartingWithBegin.stream()
                .flatMap(beginTraject -> beginTraject.stream()
                        .distinct()
                        .flatMap(currentActualBeginStop -> {
                            List<ArrayList<String>> tempStartLeg = allTrajects.stream()
                                    .filter(t -> t.contains(currentActualBeginStop))
                                    .map(t -> {
                                        int idx = t.indexOf(currentActualBeginStop);
                                        return new ArrayList<>(t.subList(idx, t.size()));
                                    })
                                    .filter(leg -> !leg.isEmpty() && leg.getFirst().equals(currentActualBeginStop))
                                    .toList();

                            return tempStartLeg.stream()
                                    .flatMap(finalStartLeg ->
                                            trajectsEndingWithEnd.stream()
                                                    .flatMap(finalEndLeg ->
                                                            findTransferPointAndCombine(
                                                                    beginTraject,
                                                                    finalStartLeg,
                                                                    finalEndLeg,
                                                                    currentActualBeginStop,
                                                                    overallEnd
                                                            ).stream()
                                                    )
                                    );
                        })
                )
                .distinct()
                .toList();

        ArrayList<ArrayList<String>> finalCombinedRoutes = new ArrayList<>();
        for (CombinedLeg cl : foundRoutes) {
            finalCombinedRoutes.add(new ArrayList<>(cl.getPath()));
        }

        return finalCombinedRoutes;
    }
    private static List<CombinedLeg> findTransferPointAndCombine(
            List<String> finalStartLeg,
            List<String> startLeg,
            List<String> endLeg,
            String overallBegin,
            String overallEnd) {

        List<CombinedLeg> combinedResult = new ArrayList<>();

        for (String potentialTransferStop : startLeg) {
            int i = startLeg.indexOf(potentialTransferStop);

            if (potentialTransferStop.equals(overallEnd)) {
                continue;
            }
            if (potentialTransferStop.equals(overallBegin) && i == startLeg.size() - 1) {
                continue;
            }

            int transferIndexInEndLeg = -1;
            for (int j = 0; j < endLeg.size() - 1; j++) {
                if (endLeg.get(j).equals(potentialTransferStop)) {
                    transferIndexInEndLeg = j;
                    break;
                }
            }

            if (transferIndexInEndLeg != -1) {
                ArrayList<String> newPath = new ArrayList<>();

                for (int k = 0; k <= i; k++) {
                    newPath.add(startLeg.get(k));
                }

                for (int k = transferIndexInEndLeg + 1; k < endLeg.size(); k++) {
                    newPath.add(endLeg.get(k));
                }

                if (!newPath.isEmpty() && newPath.getFirst().equals(overallBegin) && newPath.getLast().equals(overallEnd) && newPath.size() > (potentialTransferStop.equals(overallBegin) ? 1 : 2)) {
                    List<String> finalPath = new ArrayList<>(finalStartLeg);
                    finalPath.addAll(newPath);
                    finalPath = finalPath.stream().distinct().toList();
                    combinedResult.add(new CombinedLeg(finalPath, potentialTransferStop));
                }
            }
        }
        return combinedResult;
    }

    public static void main(String[] args) {
        JSONArray busTrips = JSONHandler.loadJsonArray("/JSON/busTraject.json");
        ArrayList<Traject> trajects = MappingBusTrajectTest.getBusTrajectFromJSONArray(busTrips);
        List<ArrayList<String>> allTrajects = trajects.stream()
                .map(Traject::getOnlyStopsNameTraject)
                .toList();

        String beginStation = "Schiphol, Airport Plaza";
        String eindStation = "Amsterdam, Spaarndammerbuurt";

        ArrayList<ArrayList<String>> possibleRoutes = findRoutesWithOneTransfer(beginStation, eindStation, allTrajects);

        if (possibleRoutes.isEmpty()) {
            System.out.println("Geen routes gevonden van " + beginStation + " naar " + eindStation + " met één overstap via deze logica.");
        } else {
            System.out.println("Mogelijke routes van " + beginStation + " naar " + eindStation + " met één overstap:");
            for (ArrayList<String> route : possibleRoutes) {
                System.out.println(route);
            }
        }
    }
}