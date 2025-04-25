package org.example.ovapp;

import org.json.simple.JSONArray;

import java.net.HttpURLConnection;

public interface TransportAPI {
    HttpURLConnection getURLConnection();

    JSONArray getDepartures(String unicode);
}
