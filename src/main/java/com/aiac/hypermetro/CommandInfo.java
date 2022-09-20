package com.aiac.hypermetro;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandInfo {
    private static final Pattern COMMAND_PATTERN = Pattern.compile("^/([\\w-]+)(?:\\s+(?:\"[\\w& .-]+\"|[\\w&.-]+)){0,4}$");
    private static final Pattern ARGUMENT_PATTERN = Pattern.compile("\\s+(?:\"([\\w& .-]+)\"|([\\w&.-]+))");
    private final CommandName command;
    private final List<String> arguments;

    private CommandInfo(CommandName command, List<String> arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public CommandName getCommand() {
        return command;
    }

    public String getFirstLineName() {
        return argumentByIndexOrNull(0);
    }

    public String getFirstStationName() {
        return argumentByIndexOrNull(1);
    }

    public String getSecondLineName() {
        return argumentByIndexOrNull(2);
    }

    public String getSecondStationName() {
        return argumentByIndexOrNull(3);
    }

    public int getTime() {
        String argument = argumentByIndexOrNull(2);
        return argument != null ? Integer.parseInt(argument) : Integer.MAX_VALUE;
    }

    private String argumentByIndexOrNull(int index) {
        return arguments.size() > index ? arguments.get(index) : null;
    }

    public static CommandInfo ofUserInput(String input) {
        Matcher matcher = COMMAND_PATTERN.matcher(input);
        if (matcher.matches()) {
            CommandName commandName = CommandName.of(matcher.group(1));
            if (commandName != null) {
                matcher = ARGUMENT_PATTERN.matcher(input);
                List<String> arguments = matcher.results()
                        .map(r -> r.group(1) == null ? r.group(2) : r.group(1))
                        .collect(Collectors.toList());
                return new CommandInfo(commandName, arguments);
            }
        }
        return null;
    }
}
