package org.example.ovapp;

public class Station {
    String name;
    String uicCode;

    public Station(String uicCode, String name) {
        this.name = name;
        this.uicCode = uicCode;
    }

    @Override
    public String toString() {
        return name;
    }


}
