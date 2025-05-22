package org.example.ovapp;

import org.json.simple.parser.JSONParser;

public class Instance {
    public static final String urlTips = "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v3/trips?fromStation=%s&toStation=%s&dateTime=%s&searchForArrival=%s";
    public static final String key = "4f0e251055ca44b791ea6d3cc4babbee";
    public static final JSONParser parser = new JSONParser();

}