package org.example.ovapp.nsApi;

import org.example.ovapp.Instance;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NSApiSecurity {

    public static HttpURLConnection getConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Cache-Control", "no-cache");

        connection.setRequestProperty("Ocp-Apim-Subscription-Key", Instance.key);

        connection.setRequestMethod("GET");
        return connection;
    }
}
