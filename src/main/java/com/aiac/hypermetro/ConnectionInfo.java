package com.aiac.hypermetro;

public class ConnectionInfo {
    private String line;
    private String station;

    public ConnectionInfo(String line, String station) {
        this.line = line;
        this.station = station;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}