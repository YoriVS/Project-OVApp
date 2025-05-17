package org.example.ovapp.handler;

import org.example.ovapp.Instance;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIHandler {
    /// return the result of the url get request
    public static HttpURLConnection getHttpURLConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Cache-Control", "no-cache");

        connection.setRequestProperty("Ocp-Apim-Subscription-Key", Instance.key);

        connection.setRequestMethod("GET");
        return connection;
    }

}
