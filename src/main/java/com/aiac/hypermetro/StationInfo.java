package com.aiac.hypermetro;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StationInfo {
    private String name;
    @SerializedName(value = "prev")
    private List<String> prevStations;
    @SerializedName(value = "next")
    private List<String> nextStations;
    @SerializedName(value = "transfer")
    private List<ConnectionInfo> connectionsInfo;
    private int time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPrevStations() {
        return prevStations;
    }

    public void setPrevStations(List<String> prevStations) {
        this.prevStations = prevStations;
    }

    public List<String> getNextStations() {
        return nextStations;
    }

    public void setNextStations(List<String> nextStations) {
        this.nextStations = nextStations;
    }

    public List<ConnectionInfo> getConnectionsInfo() {
        return connectionsInfo;
    }

    public void setConnectionsInfo(List<ConnectionInfo> connectionsInfo) {
        this.connectionsInfo = connectionsInfo;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
