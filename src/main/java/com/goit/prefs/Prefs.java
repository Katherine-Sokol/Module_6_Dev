package com.goit.prefs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Prefs {

    public static final String DEFAULT_PREFS_URL = "src/main/resources/prefs.json";
    public static final String JDBC_CONNECTION_URL = "jdbcUrl";

    private Map<String, Object> stringObjectMap = new HashMap<>();

    public Prefs() {
        this(DEFAULT_PREFS_URL);
    }

    public Prefs(String url) {
        try {
            String json = Files.readString(Paths.get(url));
            stringObjectMap = new Gson().fromJson(json, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getPref(String key) {
        return stringObjectMap.get(key);
    }

    public String getString(String key) {
        return getPref(key).toString();
    }

}
