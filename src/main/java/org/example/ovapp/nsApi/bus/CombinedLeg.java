package org.example.ovapp.nsApi.bus;

import org.example.ovapp.traject.Stop;

import java.util.List;
import java.util.Objects;

public class CombinedLeg {
    private List<String> path;
    private String transferStop;

    CombinedLeg(List<String> path, String transferStop) {
        this.path = path;
        this.transferStop = transferStop;
    }

    // Optioneel: equals en hashCode als je ze in een Set wilt stoppen om duplicaten te filteren
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CombinedLeg that = (CombinedLeg) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    @Override
    public String toString() {
        return path.toString() + " (via " + transferStop + ")";
    }

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public String getTransferStop() {
        return transferStop;
    }

    public void setTransferStop(String transferStop) {
        this.transferStop = transferStop;
    }
}