package org.example.ovapp.handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIHandler {
    /// return the result of the url get request
    public static HttpURLConnection getHttpURLConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //Request headers
        connection.setRequestProperty("Cache-Control", "no-cache");

        connection.setRequestProperty("Ocp-Apim-Subscription-Key", "4f0e251055ca44b791ea6d3cc4babbee");

        connection.setRequestMethod("GET");
        return connection;
    }
    /// return the result of the url get request with added parameters
    public static HttpURLConnection getHttpURLConnection(String urlString, String params) throws IOException {
        URL url = new URL(urlString + params);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //Request headers
        connection.setRequestProperty("Cache-Control", "no-cache");

        connection.setRequestProperty("Ocp-Apim-Subscription-Key", "4f0e251055ca44b791ea6d3cc4babbee");

        connection.setRequestMethod("GET");
        return connection;
    }
}
