package com.aiac.hypermetro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Line {

    private static final String DELIMITER = " - ";
    private final String name;
    private final Map<String, Station> stations;

    public Line(String name, List<StationInfo> stationNames) {
        this.name = name;
        stations = new HashMap<>();
        addExistingStations(stationNames);
    }

    public String getName() {
        return name;
    }

    public void appendStation(String stationName, int time) {
        Station station = new Station(stationName, time);
        stations.put(stationName, station);
    }

    public void addHeadStation(String stationName, int time) {
        appendStation(stationName, time);
    }

    public void removeStation(String stationName) {
        Station station = getStationByName(stationName);
        if (station != null) {
            Set<Station> stations = station.getConnections().keySet();
            for (Station s : stations) {
                s.removeConnection(station);
            }
            station.getPrevStations().forEach(s -> s.removeNextStation(station));
            station.getNextStations().forEach(s -> s.removePrevStation(station));
        }
    }

    public Station getStationByName(String stationName) {
        return stations.get(stationName);
    }

    public void print() {
        if (!stations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("depot").append(System.lineSeparator());
            for (Station station : stations.values()) {
                sb.append(station.getName());
                if (station.hasConnections()) {
                    sb.append(DELIMITER);
                    String connections = station.getConnections().entrySet().stream()
                            .map(e -> String.format("%s (%s)", e.getKey().getName(), e.getValue().getName()))
                            .collect(Collectors.joining(DELIMITER));
                    sb.append(connections);
                }
                sb.append(System.lineSeparator());
            }
            sb.append("depot").append(System.lineSeparator());
            System.out.println(sb);
        }
    }

    private void addExistingStations(List<StationInfo> stationsInfo) {
        if (!stationsInfo.isEmpty()) {
            // Create Stations
            stationsInfo.forEach(info -> stations.put(info.getName(), new Station(info.getName(), info.getTime())));
            // Connect next and previous stations
            for (StationInfo stationInfo : stationsInfo) {
                Station current = getStationByName(stationInfo.getName());
                stationInfo.getNextStations().forEach(next -> current.addNextStation(getStationByName(next)));
                stationInfo.getPrevStations().forEach(prev -> current.addPrevStation(getStationByName(prev)));
            }
        }
    }

}
