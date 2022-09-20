package com.aiac.hypermetro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetroService {
    private final Map<String, Line> lines;

    public MetroService(Map<String, List<StationInfo>> linesInfo) {
        lines = new HashMap<>();
        addExistingLinesAndStations(linesInfo);
    }

    private void addExistingLinesAndStations(Map<String, List<StationInfo>> linesInfo) {
        // Create lines and stations
        linesInfo.forEach((line, stations) -> lines.put(line, new Line(line, stations)));
        // Connect lines and stations
        for (Map.Entry<String, List<StationInfo>> lines : linesInfo.entrySet()) {
            for (StationInfo stationInfo : lines.getValue()) {
                if (!stationInfo.getConnectionsInfo().isEmpty()) {
                    for (ConnectionInfo connectionInfo : stationInfo.getConnectionsInfo()) {
                        connectStations(lines.getKey(), stationInfo.getName(), connectionInfo.getLine(), connectionInfo.getStation());
                    }
                }
            }
        }
    }

    public void appendStation(String lineName, String stationName, int time) {
        Line line = lines.get(lineName);
        if (line != null) {
            line.appendStation(stationName, time);
        }
    }

    public void addHeadStation(String lineName, String stationName, int time) {
        Line line = lines.get(lineName);
        if (line != null) {
            line.addHeadStation(stationName, time);
        }
    }

    public void removeStation(String lineName, String stationName) {
        Line line = lines.get(lineName);
        if (line != null) {
            line.removeStation(stationName);
        }
    }

    public void findShortestRoute(String fromLine, String fromStation, String toLine, String toStation) {
        Line line1 = lines.get(fromLine);
        Line line2 = lines.get(toLine);
        if (line1 != null && line2 != null) {
            Station station1 = line1.getStationByName(fromStation);
            Station station2 = line2.getStationByName(toStation);
            if (station1 != null && station2 != null) {
                List<String> shortestPath = RouteUtils.findShortestPathBfs(station1, station2);
                shortestPath.forEach(System.out::println);
            }
        }
    }

    public void findFastestRoute(String fromLine, String fromStation, String toLine, String toStation) {
        Line line1 = lines.get(fromLine);
        Line line2 = lines.get(toLine);
        if (line1 != null && line2 != null) {
            Station station1 = line1.getStationByName(fromStation);
            Station station2 = line2.getStationByName(toStation);
            if (station1 != null && station2 != null) {
                List<String> pathAndTime = RouteUtils.fastestRouteDijkstra(station1, station2);
                pathAndTime.forEach(System.out::println);
            }
        }
    }

    public void connectStations(String fromLine, String fromStation, String toLine, String toStation) {
        Line line1 = lines.get(fromLine);
        Line line2 = lines.get(toLine);
        if (line1 != null && line2 != null) {
            Station station1 = line1.getStationByName(fromStation);
            Station station2 = line2.getStationByName(toStation);
            if (station1 != null && station2 != null) {
                station1.addConnection(station2, line2);
                station2.addConnection(station1, line1);
            }
        }
    }

    public void outputLine(String lineName) {
        Line line = lines.get(lineName);
        if (line != null) {
            line.print();
        }
    }

}
