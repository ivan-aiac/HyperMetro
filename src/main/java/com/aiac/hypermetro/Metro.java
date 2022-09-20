package com.aiac.hypermetro;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.aiac.hypermetro.CommandName.*;

public class Metro {
    private final MetroService metroService;
    private final Map<CommandName, Command> commands;
    private boolean running;
    private final Scanner scanner = new Scanner(System.in);

    public Metro(Path filePath) throws IllegalArgumentException {
        metroService = new MetroService(JsonParseUtils.parseMetroLinesFromJsonFile(filePath));
        commands = new HashMap<>();
        running = false;
        loadCommands();
    }

    public Metro(String filePath) throws IllegalArgumentException {
        this(Paths.get(filePath));
    }


    private void loadCommands() {
        commands.put(APPEND, this::appendStationCommand);
        commands.put(ADD_HEAD,this::addHeadStationCommand);
        commands.put(REMOVE, this::removeStationCommand);
        commands.put(OUTPUT, this::outputLineCommand);
        commands.put(CONNECT,this::connectCommand);
        commands.put(ROUTE, this::routeCommand);
        commands.put(FAST_ROUTE,this::fastestRouteCommand);
        commands.put(EXIT, this::exit);
    }

    public void start() {
        if (!running) {
            running = true;
            while (running) {
                handleCommands();
            }
        }
    }

    private void handleCommands() {
        CommandInfo commandInfo = CommandInfo.ofUserInput(scanner.nextLine());
        if (commandInfo != null) {
            Command command = commands.get(commandInfo.getCommand());
            command.execute(commandInfo);
        } else {
            System.out.println("Invalid command");
        }
    }

    private void appendStationCommand(CommandInfo commandInfo) {
        metroService.appendStation(commandInfo.getFirstLineName(),
                commandInfo.getFirstStationName(), commandInfo.getTime());
    }

    private void addHeadStationCommand(CommandInfo commandInfo) {
        metroService.addHeadStation(commandInfo.getFirstLineName(),
                commandInfo.getFirstStationName(), commandInfo.getTime());
    }

    private void outputLineCommand(CommandInfo commandInfo) {
        metroService.outputLine(commandInfo.getFirstLineName());
    }

    private void removeStationCommand(CommandInfo commandInfo) {
        metroService.removeStation(commandInfo.getFirstLineName(), commandInfo.getFirstStationName());
    }

    private void connectCommand(CommandInfo commandInfo) {
        metroService.connectStations(commandInfo.getFirstLineName(), commandInfo.getFirstStationName(),
                commandInfo.getSecondLineName(), commandInfo.getSecondStationName());
    }

    private void routeCommand(CommandInfo commandInfo) {
        metroService.findShortestRoute(commandInfo.getFirstLineName(), commandInfo.getFirstStationName(),
                commandInfo.getSecondLineName(), commandInfo.getSecondStationName());
    }

    private void fastestRouteCommand(CommandInfo commandInfo) {
        metroService.findFastestRoute(commandInfo.getFirstLineName(), commandInfo.getFirstStationName(),
                commandInfo.getSecondLineName(), commandInfo.getSecondStationName());
    }

    private void exit(CommandInfo commandInfo) {
        if (running) {
            running = false;
        }
    }

}
