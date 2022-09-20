package com.aiac.hypermetro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Station {

    private final String name;
    private final List<Station> nextStations;
    private final List<Station> prevStations;
    private final int time;
    private final Map<Station, Line> connections;

    public Station(String name, int time) {
        this.name = name;
        this.time = time;
        nextStations = new ArrayList<>();
        prevStations = new ArrayList<>();
        connections = new HashMap<>();
    }

    public int getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public List<Station> getNextStations() {
        return nextStations;
    }

    public List<Station> getPrevStations() {
        return prevStations;
    }

    public boolean hasNextStations() {
        return !nextStations.isEmpty();
    }

    public void addNextStation(Station next) {
        nextStations.add(next);
    }

    public boolean hasPrevStations() {
        return !prevStations.isEmpty();
    }

    public void addPrevStation(Station prev) {
        prevStations.add(prev);
    }

    public void removeNextStation(Station station) {
        nextStations.remove(station);
    }

    public void removePrevStation(Station station) {
        prevStations.remove(station);
    }

    public void addConnection(Station station, Line line) {
        connections.put(station, line);
    }

    public boolean hasConnections() {
        return !connections.isEmpty();
    }

    public boolean containsConnection(Station station) {
        return connections.containsKey(station);
    }

    public Line getConnectionLineOf(Station station) {
        return connections.get(station);
    }

    public void removeConnection(Station station) {
        connections.remove(station);
    }

    public Map<Station, Line> getConnections() {
        return connections;
    }
}
