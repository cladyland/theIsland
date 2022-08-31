package service;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import exceptions.FileProcessingException;
import lombok.Getter;
import resources.GameObjectName;
import resources.KeysProperties;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Properties;

import static resources.KeysProperties.COUNT;
import static resources.KeysProperties.MAX;
import static resources.KeysProperties.WEIGHT;

public class AppProperties {
    @Getter
    private LinkedHashMap<String, LinkedHashMap<String, Integer>> animalRation;
    private final Properties PROPERTIES;

    {
        String propertiesPath = "src/resources/app.properties";
        PROPERTIES = new Properties();
        try {
            FileReader reader = new FileReader(propertiesPath);
            PROPERTIES.load(reader);
        } catch (IOException ex) {
            throw new FileProcessingException("Failed to read file: " + propertiesPath, ex);
        }

        String rationPath = "src/resources/ration.yaml";
        var rationFile = new File(rationPath);
        var mapper = new YAMLMapper();
        try {
            animalRation = mapper.readValue(rationFile, LinkedHashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private AppProperties() {
    }

    public static AppProperties getInstance() {
        return InstanceHolder.instance;
    }

    public <E> String getAppProperty(E name, KeysProperties... keys) {
        return PROPERTIES.getProperty(getPropertiesName(name, keys));
    }

    public int getMaxCountOnArea(GameObjectName objectName) {
        return Integer.parseInt(PROPERTIES
                .getProperty(getPropertiesName(objectName, MAX, COUNT)));
    }

    public double getWeight(GameObjectName objectName) {
        return Double.parseDouble(getAppProperty(objectName, WEIGHT));
    }

    private <E> String getPropertiesName(E name, KeysProperties... keys) {
        var propertiesName = new StringBuilder();
        propertiesName.append(name).append(".");

        for (KeysProperties key : keys) {
            propertiesName.append(key);
            propertiesName.append(".");
        }

        int lastPoint = propertiesName.length() - 1;
        if (propertiesName.length() > 0) {
            propertiesName.deleteCharAt(lastPoint);
        }
        return propertiesName.toString().toLowerCase();
    }

    private static class InstanceHolder {
        public static AppProperties instance = new AppProperties();
    }
}
