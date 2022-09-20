package com.aiac.hypermetro;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        try {
            if (args[0] != null) {
                Metro metro = new Metro(args[0]);
                metro.start();
            } else {
                URL resource = Thread.currentThread().getContextClassLoader().getResource("london.json");
                if (resource != null) {
                    Metro metro = new Metro(Path.of(resource.toURI()));
                    metro.start();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}