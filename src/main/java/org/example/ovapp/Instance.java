package org.example.ovapp;

import org.json.simple.parser.JSONParser;

public class Instance {
    public static final String urlTips = "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v3/trips?fromStation=%s&toStation=%s&dateTime=%s&searchForArrival=%s";
    public static final String key = "4f0e251055ca44b791ea6d3cc4babbee";
    public static final JSONParser parser = new JSONParser();
    public static final byte[] salt = {94, -86, 123, -15, 21, 72, 77, 53, -42, -70, -6, 45, 10, 102, -33, -21};

}