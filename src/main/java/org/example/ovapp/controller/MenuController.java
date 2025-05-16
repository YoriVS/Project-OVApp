package org.example.ovapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.ovapp.*;
import org.example.ovapp.handler.APIHandler;
import org.example.ovapp.handler.ScreenHandler;
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
    @FXML
    public ListView<Traject> favoriteTrajectList;
    @FXML
    public TextField startPointTextBlock;
    @FXML
    public ComboBox timeTypeSelector;
    @FXML
    public TextField timeInput;
    @FXML
    public TextField endPointTextBlock;

    private static List<Traject> trajects = new ArrayList<>();
    private static int moreButtonTimeClicked = 0;

    @FXML
    public void initialize() {
        trajectList.getItems().clear();
        timeInput.setText(TimeHandler.getCurrentRFC3339Time().format(TimeHandler.HH_MM_FORMATTER));
        startPointTextBlock.setText("Amsterdam Centraal");
        endPointTextBlock.setText("Groningen");

        if (!trajects.isEmpty()) {
            trajectList.getItems().addAll(trajects);
        }
    }

    /// Load new screen with the selected traject
    @FXML
    public void chooseButtonClicked(ActionEvent actionEvent) throws IOException {
        Traject selectedTraject = trajectList.getSelectionModel().getSelectedItem();
        if (selectedTraject != null) {
            OVApp.currentTraject = selectedTraject;
            ScreenHandler.changeScreen("traject-page", "Traject", ScreenHandler.getStage(actionEvent), Constant.screenWidth, Constant.screenHeight);
        } else {
            System.out.println("Please select a Traject.");
        }
    }

    @FXML
    public void chooseFavoriteButtonClicked(ActionEvent actionEvent) {
    }

    /// Load the traject
    @FXML
    public void searchTrajectButtonClicked(ActionEvent actionEvent) {
        if (startPointTextBlock.getText().isEmpty() || endPointTextBlock.getText().isEmpty() || timeInput.getText().isEmpty() || timeTypeSelector.getSelectionModel().getSelectedItem() == null) {
            if (startPointTextBlock.getText().isEmpty()) {
                startPointTextBlock.setPromptText("Please enter start point");
            }
            if (endPointTextBlock.getText().isEmpty()) {
                endPointTextBlock.setPromptText("Please enter end point");
            }
            if (timeInput.getText().isEmpty()) {
                timeInput.setPromptText("Please enter time");
            }
            trajectList.getItems().clear();
        } else {
            try {
                moreButtonTimeClicked = 0;
                trajectList.getItems().clear();
                OffsetDateTime baseDateTime = OffsetDateTime.parse(TimeHandler.convertTimeToRFC3339(timeInput.getText()));
                String startPoint = startPointTextBlock.getText();
                String endPoint = endPointTextBlock.getText();
                boolean departureOrNot = timeTypeSelector.getSelectionModel().getSelectedItem().equals("Arrival");
                trajects = getTrips(startPoint, endPoint, baseDateTime.toString(), departureOrNot);
                trajectList.getItems().addAll(trajects);
            } catch (IOException | ParseException | DateTimeParseException e) {
                System.err.println("Error searching for trajects: " + e.getMessage());
                trajectList.getItems().clear();
            }
        }
    }

    /// Load more traject
    @FXML
    public void moreTrajectsButtonClicked(ActionEvent actionEvent) {
        moreButtonTimeClicked += 1;
        try {
            trajectList.getItems().clear();
            OffsetDateTime baseDateTime = OffsetDateTime.parse(TimeHandler.convertTimeToRFC3339(timeInput.getText()));
            String startPoint = startPointTextBlock.getText();
            String endPoint = endPointTextBlock.getText();
            boolean departureOrNot = timeTypeSelector.getSelectionModel().getSelectedItem().equals("Arrival");
            trajects.addAll(getTrips(startPoint, endPoint, baseDateTime.plusHours(moreButtonTimeClicked).toString(), departureOrNot));
            trajectList.getItems().addAll(trajects);
        } catch (IOException | ParseException | DateTimeParseException e) {
            System.err.println("Error searching for trajects: " + e.getMessage());
            trajectList.getItems().clear();
        }
    }

    /// Get trips from NS API
    private ArrayList<Traject> getTrips(String fromStation, String toStation, String dateTime, boolean departureOrArrival) throws IOException, ParseException {

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
    }
}
