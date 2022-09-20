package com.aiac.hypermetro;

public class StationNode implements Comparable<StationNode>{

    private final Station station;
    private int distance;

    public StationNode(Station station, int distance) {
        this.station = station;
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public Station getStation() {
        return station;
    }

    public void addDistance(int amount) {
        distance += amount;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(StationNode stationNode) {
        return Integer.compare(distance, stationNode.getDistance());
    }

}
