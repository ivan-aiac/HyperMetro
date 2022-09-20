package com.aiac.hypermetro;

import java.util.Arrays;

public enum CommandName {
    APPEND("append"),
    ADD_HEAD("add-head"),
    REMOVE("remove"),
    OUTPUT("output"),
    CONNECT("connect"),
    ROUTE("route"),
    FAST_ROUTE("fastest-route"),
    EXIT("exit");

    private final String name;

    CommandName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CommandName of(String name) {
        return Arrays.stream(CommandName.values())
                .filter(command -> name.equalsIgnoreCase(command.getName()))
                .findFirst().orElse(null);
    }
}
