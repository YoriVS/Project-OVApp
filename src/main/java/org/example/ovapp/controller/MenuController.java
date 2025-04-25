package org.example.ovapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.ovapp.*;
import org.example.ovapp.handler.APIHandler;
import org.example.ovapp.handler.JSONHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MenuController {
    public ListView trajectList;
    public ListView favoriteTrajectList;
    public TextField endPointTextBlock;
    public TextField startPointTextBlock;

    @FXML
    public void chooseButtonClicked(ActionEvent actionEvent) throws IOException {
        OVApp.currentTraject = (Traject) trajectList.getSelectionModel().getSelectedItem();
        System.out.println(OVApp.currentTraject.getName());
        Screen.changeScreen("traject-page", "Traject", Screen.getStage(actionEvent), Constant.screenWidth, Constant.screenHeight);
    }

    @FXML
    public void initialize() throws IOException, ParseException {
        trajectList.getItems().clear();
//        trajectList.getItems().addAll(getTrajectFromApi("hey"));
    }

    public void chooseFavoriteButtonClicked(ActionEvent actionEvent) {
    }

    public void searchTrajectButtonClicked(ActionEvent actionEvent) throws IOException, ParseException {
        if (startPointTextBlock.getText().isEmpty() || endPointTextBlock.getText().isEmpty()) {
            if (startPointTextBlock.getText().isEmpty()) {
                startPointTextBlock.setPromptText("Enter Start Point");
            } else if (endPointTextBlock.getText().isEmpty()) {
                startPointTextBlock.setPromptText("Enter End Point");
            }
        } else {
            trajectList.getItems().clear();
            trajectList.getItems().addAll(getTrajectFromApi(startPointTextBlock.getText(), endPointTextBlock.getText()));

        }


    }

    private List<Traject> getTrajectFromApi(String stationStartName, String stationEndName) throws IOException, ParseException {
        List<Traject> trajects = new ArrayList<>();
        JSONArray jsonArray = JSONHandler.loadJsonArray("src/main/resources/JSON/stations.json");
        String uicCodeDeparture = "default";
        String uicCodeArrival = "default";
        String timeDeparture = getCurrentRFC3339Time();
        String timeArrival = "2025-04-25T011:31:32+02:00";

        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            if (jsonObject.get("station_name").equals(stationStartName)) {
                uicCodeDeparture = jsonObject.get("uic_code").toString();
            } else if (jsonObject.get("station_name").equals(stationEndName)) {
                uicCodeArrival = jsonObject.get("uic_code").toString();
            }
        }

        // uicCodeDeparture = stations.getByName(stationStartName)

        TransportApi api = new NsTransportAPI(4f0e251055ca44b791ea6d3cc4babbee);
        //TransportApi api = new ArrivaTransportAPI(4f0e251055ca44b791ea6d3cc4babbee);

        Collection a = new ArrayList();
        List b = new ArrayList();
        b.
        api.getdeparturestring();

        String urlStringDeparture = "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v2/departures";
        String paramsDeparture = "?lang=NL&uicCode="+uicCodeDeparture+"&dateTime="+timeDeparture+"&maxJourneys=10000";
        HttpURLConnection connectionDeparture = APIHandler.getHttpURLConnection(urlStringDeparture, paramsDeparture);

        JSONObject departureJSON = (JSONObject) new JSONParser().parse(new InputStreamReader(connectionDeparture.getInputStream()));
        JSONObject payloadDeparture = (JSONObject) departureJSON.get("payload");
        JSONArray departures = (JSONArray) payloadDeparture.get("departures");
        connectionDeparture.disconnect();

        String urlStringArrival = "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v2/arrivals";
        String paramsArrival = "?lang=NL&uicCode="+uicCodeArrival+"&dateTime="+timeArrival+"&maxJourneys=10000";
        HttpURLConnection connection = APIHandler.getHttpURLConnection(urlStringArrival, paramsArrival);

        JSONObject arrivalJSON = (JSONObject) new JSONParser().parse(new InputStreamReader(connection.getInputStream()));
        JSONObject payloadArrival = (JSONObject) arrivalJSON.get("payload");
        JSONArray arrivals = (JSONArray) payloadArrival.get("arrivals");
        connection.disconnect();

        JSONArray departureArray = new JSONArray();

        for (Object object : departures) {
            JSONObject obj = (JSONObject) object;
            if (obj.get("direction").equals(stationEndName)) {
                departureArray.add(obj);
            }
        }

        for (Object object : arrivals) {
            JSONObject obj = (JSONObject) object;
            JSONObject productArrival = (JSONObject) obj.get("product");
            for (Object departureObject : departureArray) {
                JSONObject departure = (JSONObject) departureObject;
                JSONObject departureProduct= (JSONObject) departure.get("product");
                if (productArrival.get("number").equals(departureProduct.get("number"))) {
                    System.out.format("Depart: %s\nArrival: %s\n",departure, obj);
                    Station departureStation = new Station(stationStartName);
                    Station arrivalStation = new Station(stationEndName);
                    JSONArray inBetweenStations = (JSONArray) departure.get("routeStations");
                    List<Station> inBetweenStationList = new ArrayList<>();
                    for (Object staionObject : inBetweenStations) {
                        JSONObject station = (JSONObject) staionObject;
                        inBetweenStationList.add(new Station(station.get("mediumName").toString()));
                    }

                    Traject traject = new Traject(
                            departureStation,
                            arrivalStation,
                            inBetweenStationList,
                            extractHourMinute(departure.get("plannedDateTime").toString()),
                            extractHourMinute(obj.get("plannedDateTime").toString()),
                            Integer.parseInt(departureProduct.get("number").toString()));

                    trajects.add(traject);
                }
            }
        }
        return trajects;
    }

    public static String getCurrentRFC3339Time() {
        OffsetDateTime now = OffsetDateTime.now();
        DateTimeFormatter rfc3339Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        return now.format(rfc3339Formatter);
    }
    public static String extractHourMinute(String rfc3339Time) {
        String timePart = rfc3339Time;
        int plusIndex = rfc3339Time.indexOf('+');
        int minusIndex = rfc3339Time.indexOf('-');

        int offsetIndex = Math.max(plusIndex, minusIndex);

        if (offsetIndex > 0 && rfc3339Time.length() - offsetIndex == 5) { // Check if offset is like +HHMM or -HHMM
            timePart = rfc3339Time.substring(0, offsetIndex + 3) + ":" + rfc3339Time.substring(offsetIndex + 3);
        }

        OffsetDateTime offsetDateTime = OffsetDateTime.parse(timePart);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return offsetDateTime.format(timeFormatter);
    }
}
