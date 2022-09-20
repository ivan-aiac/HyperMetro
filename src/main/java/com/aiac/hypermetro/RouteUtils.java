package com.aiac.hypermetro;

import java.util.*;

public class RouteUtils {

    public static List<String> findShortestPathBfs(Station fromStation, Station toStation) {
        Queue<Station> queue = new ArrayDeque<>();
        Set<Station> visited = new HashSet<>();
        Map<Station, Station> parents = new HashMap<>();

        queue.offer(fromStation);
        visited.add(fromStation);

        while (!queue.isEmpty()) {
            Station station = queue.poll();
            if (station.equals(toStation)) {
                break;
            }
            List<Station> nextStations = new ArrayList<>();
            if (station.hasConnections()) {
                nextStations.addAll(station.getConnections().keySet());
            }
            if (station.hasPrevStations()) {
                nextStations.addAll(station.getPrevStations());
            }
            if (station.hasNextStations()) {
                nextStations.addAll(station.getNextStations());
            }
            nextStations.forEach(s -> {
                if (visited.add(s)) {
                    queue.offer(s);
                    parents.put(s, station);
                }
            });
        }

        return calculatePathToStation(toStation, parents);
    }

    private static List<String> calculatePathToStation(Station toStation, Map<Station, Station> parents) {
        List<String> shortestPath = new ArrayList<>();
        Station station = toStation;
        while (station != null) {
            shortestPath.add(station.getName());
            Station parent = parents.get(station);
            if (parent != null && parent.containsConnection(station)) {
                shortestPath.add(String.format("Transition to line %s", parent.getConnectionLineOf(station).getName()));
            }
            station = parent;
        }
        Collections.reverse(shortestPath);
        return shortestPath;
    }

    public static List<String> fastestRouteDijkstra(Station fromStation, Station toStation) {
        Set<Station> visited = new HashSet<>();
        Map<Station, Station> parents = new HashMap<>();
        Map<Station, Integer> distances = new HashMap<>();
        PriorityQueue<StationNode> queue = new PriorityQueue<>();

        distances.put(fromStation, 0);
        StationNode from = new StationNode(fromStation, 0);
        queue.offer(from);
        while (!queue.isEmpty()) {
            StationNode current = queue.poll();
            if (toStation.equals(current.getStation())) {
                break;
            }
            if (visited.add(current.getStation())) {
                List<StationNode> nextNodes = findNextNodes(current.getStation());
                for (StationNode next : nextNodes) {
                    int currentDist = distances.get(current.getStation());
                    if (distances.containsKey(next.getStation())) {
                        int storedDist = distances.get(next.getStation());
                        int currentPlusNext = currentDist + next.getDistance();
                        if (currentPlusNext < storedDist) {
                            distances.put(next.getStation(), currentPlusNext);
                            next.setDistance(currentPlusNext);
                            parents.put(next.getStation(), current.getStation());
                            queue.offer(next);
                        }
                    } else {
                        next.addDistance(currentDist);
                        distances.put(next.getStation(), next.getDistance());
                        queue.offer(next);
                        parents.put(next.getStation(), current.getStation());
                    }
                }
            }
        }
        List<String> pathAndTime = calculatePathToStation(toStation, parents);
        pathAndTime.add(String.format("Total: %d minutes in the way", distances.get(toStation)));
        return pathAndTime;
    }

    private static List<StationNode> findNextNodes(Station current) {
        List<StationNode> nodes = new ArrayList<>();
        if (current.hasConnections()) {
            current.getConnections().keySet()
                    .forEach(s -> nodes.add(new StationNode(s, 5)));
        }
        if (current.hasNextStations()) {
            current.getNextStations()
                    .forEach(nextStation -> nodes.add(new StationNode(nextStation, current.getTime())));
        }
        if (current.hasPrevStations()) {
            current.getPrevStations()
                    .forEach(prevStation -> nodes.add(new StationNode(prevStation, prevStation.getTime())));
        }
        return nodes;
    }
}
