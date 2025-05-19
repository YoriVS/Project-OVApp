package org.example.ovapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.ovapp.*;
import org.example.ovapp.handler.APIHandler;
import org.example.ovapp.handler.ScreenHandler;
import org.example.ovapp.traject.Route;
import org.example.ovapp.traject.Station;
import org.example.ovapp.traject.Stop;
import org.example.ovapp.traject.Traject;
import org.example.ovapp.handler.TimeHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MenuController {
    /// Load FXML items
    @FXML
    public ListView<Traject> trajectList;  // Use generic type for better type safety

    private static final List<Traject> trajects = new ArrayList<>();
    private static int moreButtonTimeClicked = 0;
    private Route selectedRoute;

    @FXML
    public void initialize() {
        trajectList.getItems().clear();
        selectedRoute = ChoseController.selectedRoute;
        trajectList.getItems().addAll(getTrips(selectedRoute.getStartPoint(), selectedRoute.getEndPoint(), TimeHandler.convertTimeToRFC3339(selectedRoute.getTime()), selectedRoute.getDepartureOrNot()));
    }

    /// Load new screen with the selected traject
    @FXML
    public void chooseButtonClicked() {
        Traject selectedTraject = trajectList.getSelectionModel().getSelectedItem();
        if (selectedTraject != null) {
            OVApp.currentTraject = selectedTraject;
            ScreenHandler.changeScreen("traject-page", OVApp.currentStage);
        } else {
            System.out.println("Please select a Traject.");
        }
    }

    /// Load more traject
    @FXML
    public void moreTrajectsButtonClicked() {
        moreButtonTimeClicked += 1;
        try {
            trajectList.getItems().clear();
            OffsetDateTime baseDateTime = OffsetDateTime.parse(TimeHandler.convertTimeToRFC3339(selectedRoute.getTime()));
            trajects.addAll(getTrips(selectedRoute.getStartPoint(), selectedRoute.getEndPoint(), baseDateTime.plusHours(moreButtonTimeClicked).toString(), selectedRoute.getDepartureOrNot()));
            trajectList.getItems().addAll(trajects);
        } catch (DateTimeParseException e) {
            System.err.println("Error searching for trajects: " + e.getMessage());
            trajectList.getItems().clear();
        }
    }

    public void handleBackButtonAction() {
        ScreenHandler.changeScreen("chose-menu", OVApp.currentStage);
    }

    /// Get trips from NS API
    private ArrayList<Traject> getTrips(String fromStation, String toStation, String dateTime, boolean departureOrArrival) {
        try {
            String encodedFrom = URLEncoder.encode(fromStation, StandardCharsets.UTF_8);
            String encodedTo = URLEncoder.encode(toStation, StandardCharsets.UTF_8);

            OffsetDateTime baseDateTime = OffsetDateTime.parse(dateTime);

            ArrayList<Traject> traject1s = new ArrayList<>();

            for (int i = 0; i < 1; i++) {
                OffsetDateTime currentDateTime = baseDateTime.plusHours(i);
                String formattedDateTime = URLEncoder.encode(currentDateTime.toString(), StandardCharsets.UTF_8);

                String urlStr = String.format(Instance.urlTips, encodedFrom, encodedTo, formattedDateTime, departureOrArrival);

                HttpURLConnection connection = APIHandler.getHttpURLConnection(urlStr);
                JSONObject tripsJSON = (JSONObject) new JSONParser().parse(new InputStreamReader(connection.getInputStream()));
                JSONArray trips = (JSONArray) tripsJSON.get("trips");

                for (Object tripObj : trips) {
                    JSONObject trip = (JSONObject) tripObj;
                    String input = trip.get("uid").toString();

                    Map<String, String> values = new HashMap<>();
                    for (String part : input.split("\\|")) {
                        if (part.contains("=")) {
                            String[] keyValue = part.split("=", 2);
                            values.put(keyValue[0], keyValue[1]);
                        }
                    }
                    Stop from = new Stop(values.get("fromStation"), encodedFrom.replace("+", " "));
                    Stop to = new Stop(values.get("toStation"), encodedTo.replace("+", " "));
                    String timeDeparture = values.get("plannedFromTime");
                    String timeArrival = values.get("plannedArrivalTime");
                    String number = values.get("tripSummaryHash");
                    String totalDuration = ((Long) trip.get("plannedDurationInMinutes")).toString();
                    String transferMessage = null;
                    ArrayList<String> inBetweenTrajects = new ArrayList<>();

                    List<Stop> stations = new ArrayList<>();
                    JSONArray legs = (JSONArray) trip.get("legs");
                    for (Object leg: legs) {
                        JSONObject legJSON = (JSONObject) leg;
                        JSONArray stopJSON = (JSONArray)  legJSON.get("stops");
                        JSONArray transferMessagesJSON = (JSONArray) legJSON.get("transferMessages");
                        if (legJSON.get("duration") != null) {
                            JSONObject durationJSON = (JSONObject) legJSON.get("duration");
                            inBetweenTrajects.add((String) durationJSON.get("accessibilityValue") + " \uD83D\uDE86");
                        }
                        if (transferMessagesJSON != null) {
                            JSONObject transferMessageJSON = (JSONObject) transferMessagesJSON.getFirst();
                            transferMessage = transferMessageJSON.get("message").toString();
                        }

                        for (Object stopObj : stopJSON) {
                            JSONObject stop = (JSONObject) stopObj;
                            stations.add(new Station(
                                    (String) stop.get("uicCode"),
                                    (String) stop.get("name"),
                                    (String) stop.get("plannedArrivalDateTime"),
                                    (String) stop.get("plannedDepartureDateTime"),
                                    (String) stop.get("plannedArrivalTrack"),
                                    (String) stop.get("plannedDepartureTrack"),
                                    (stop.get("actualDepartureDateTime") == null)));

                        }
                    }
                    Traject traject = new Traject(from, to, stations, timeDeparture, timeArrival, number, transferMessage, totalDuration, inBetweenTrajects);
                    traject1s.add(traject);

                }
            }

            return traject1s;
        } catch (IOException e) {
            System.out.println("IO " + e);
            throw new RuntimeException(e);
        } catch (ParseException e) {
            System.out.println("Parsed " + e);
            throw new RuntimeException(e);
        }
    }
}
