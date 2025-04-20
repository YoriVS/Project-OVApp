package org.example.ovapp;

public class Traject {
    String from;
    String to;

    Traject(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "from: " + from + " to: " + to;
    }
}
