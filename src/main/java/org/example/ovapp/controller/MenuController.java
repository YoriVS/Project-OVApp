package org.example.ovapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.ovapp.*;
import org.example.ovapp.handler.APIHandler;
import org.example.ovapp.Station;
import org.example.ovapp.Traject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MenuController {
    @FXML
    public ListView<Traject> trajectList;  // Use generic type for better type safety
    @FXML
    public ListView<Traject> favoriteTrajectList; // Use generic type for better type safety
    @FXML
    public TextField startPointTextBlock;
    @FXML
    public TextField endPointTextBlock;

    private static final DateTimeFormatter RFC3339_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    private static final DateTimeFormatter HH_MM_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final String NS_API_BASE_URL = "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v2";
    private static List<Traject> trajects = new ArrayList<>();
    private static int moreButtonTimeClicked = 0;
    public ComboBox timeTypeSelector;
    public TextField timeInput;


    @FXML
    public void initialize() {
        trajectList.getItems().clear();
        timeInput.setText(getCurrentRFC3339Time().format(HH_MM_FORMATTER));
        startPointTextBlock.setText("Amsterdam Centraal");
        endPointTextBlock.setText("Alkmaar");

        if (!trajects.isEmpty()) {
            trajectList.getItems().addAll(trajects);
        }
    }

    @FXML
    public void chooseButtonClicked(ActionEvent actionEvent) throws IOException {
        Traject selectedTraject = trajectList.getSelectionModel().getSelectedItem();
        if (selectedTraject != null) {
            OVApp.currentTraject = selectedTraject;
            Screen.changeScreen("traject-page", "Traject", Screen.getStage(actionEvent), Constant.screenWidth, Constant.screenHeight);
        } else {
            System.out.println("Please select a Traject.");
        }
    }

    @FXML
    public void chooseFavoriteButtonClicked(ActionEvent actionEvent) {
    }

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
                OffsetDateTime baseDateTime = OffsetDateTime.parse(convertTimeToRFC3339(timeInput.getText()));
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

    private ArrayList<Traject> getTrips(String fromStation, String toStation, String dateTime, boolean departureOrArrival) throws IOException, ParseException {

        String encodedFrom = URLEncoder.encode(fromStation, "UTF-8");
        String encodedTo = URLEncoder.encode(toStation, "UTF-8");

        OffsetDateTime baseDateTime = OffsetDateTime.parse(dateTime);

        ArrayList<Traject> traject1s = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            OffsetDateTime currentDateTime = baseDateTime.plusHours(i);
            String formattedDateTime = URLEncoder.encode(currentDateTime.toString(), StandardCharsets.UTF_8);

            String urlStr = String.format(
                    "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v3/trips?fromStation=%s&toStation=%s&dateTime=%s&searchForArrival=%b",
                    encodedFrom, encodedTo, formattedDateTime, departureOrArrival);

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
                Station from = new Station(values.get("fromStation"), encodedFrom.replace("+", " "));
                Station to = new Station(values.get("toStation"), encodedTo.replace("+", " "));
                String timeDeparture = values.get("plannedFromTime");
                String timeArrival = values.get("plannedArrivalTime");
                String number = values.get("tripSummaryHash");

                List<Station> inBetweenStation = new ArrayList<>();
                JSONArray legs = (JSONArray) trip.get("legs");
                for (Object leg: legs) {
                    JSONObject legJSON = (JSONObject) leg;
                    JSONArray stopJSON = (JSONArray)  legJSON.get("stops");

                    for (Object stopObj : stopJSON) {
                        JSONObject stop = (JSONObject) stopObj;
                        inBetweenStation.add(new Station((String) stop.get("uicCode"), (String) stop.get("name")));
                    }
                }

                Traject traject = new Traject(from, to, inBetweenStation, timeDeparture, timeArrival, number);
                traject1s.add(traject);

            }
        }

        return traject1s;
    }

    public static OffsetDateTime getCurrentRFC3339Time() {
        return OffsetDateTime.now();
    }

    public static String convertTimeToRFC3339(String timeString) {
        String[] parts = timeString.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime specificTime = localDateTime.withHour(hour).withMinute(minute).withSecond(0).withNano(0);

        ZoneId zoneId = ZoneId.systemDefault();
        OffsetDateTime offsetDateTime = specificTime.atZone(zoneId).toOffsetDateTime();
        return offsetDateTime.format(RFC3339_FORMATTER);
    }

    public static String extractHourMinute(String rfc3339Time) {
        OffsetDateTime dateTime = OffsetDateTime.parse(rfc3339Time);
        return dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));

    }

    public void moreTrajects(ActionEvent actionEvent) {
        moreButtonTimeClicked += 1;
        try {
            trajectList.getItems().clear();
            OffsetDateTime baseDateTime = OffsetDateTime.parse(convertTimeToRFC3339(timeInput.getText()));
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
}
