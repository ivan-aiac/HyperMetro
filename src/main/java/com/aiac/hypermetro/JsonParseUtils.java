package com.aiac.hypermetro;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JsonParseUtils {

    public static Map<String, List<StationInfo>> parseMetroLinesFromJsonFile(Path filePath) throws IllegalArgumentException {
        Gson gson = new GsonBuilder().create();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            Map<String, List<StationInfo>> linesInfo = gson.fromJson(reader, new TypeToken<Map<String, List<StationInfo>>>(){}.getType());
            if (linesInfo.isEmpty()) {
                throw new IllegalArgumentException("Error! empty file!");
            } else {
                return linesInfo;
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error! Such a file doesn't exist!");
        } catch (JsonParseException e) {
            throw new IllegalArgumentException("Incorrect file");
        }
    }
}
