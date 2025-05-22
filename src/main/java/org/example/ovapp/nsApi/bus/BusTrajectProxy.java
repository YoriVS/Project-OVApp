package org.example.ovapp.nsApi.bus;

import org.example.ovapp.handler.JSONHandler;
import org.example.ovapp.traject.Traject;
import org.example.ovapp.traject.bus.Halte;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class BusTrajectProxy {
    private final JSONArray busTrajectJson = JSONHandler.loadJsonArray("src/main/resources/JSON/busTraject.json");

    public JSONArray getBusTrajectJson() {
        return busTrajectJson;
    }
}
