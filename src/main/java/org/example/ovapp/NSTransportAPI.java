package org.example.ovapp;

import java.net.HttpURLConnection;
import java.net.URL;

public class NSTransportAPI implements TransportAPI {
    @Override
    public HttpURLConnection getURLConnection() {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //Request headers
        connection.setRequestProperty("Cache-Control", "no-cache");

        connection.setRequestProperty("Ocp-Apim-Subscription-Key", "4f0e251055ca44b791ea6d3cc4babbee");

        connection.setRequestMethod("GET");
        return connection;
    }
}
