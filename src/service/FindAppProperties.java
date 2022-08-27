package service;

import exceptions.FileProcessingException;
import resources.GameObjectName;
import resources.KeysProperties;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static resources.KeysProperties.COUNT;
import static resources.KeysProperties.MAX;

public class FindAppProperties {
    private final Properties properties;

    {
        properties = new Properties();
        String propertiesPath = "src/resources/app.properties";
        try {
            FileReader reader = new FileReader(propertiesPath);
            properties.load(reader);
        } catch (IOException ex) {
            throw new FileProcessingException("Failed to read file: " + propertiesPath, ex);
        }
    }

    private FindAppProperties() {
    }

    public static FindAppProperties getInstance() {
        return InstanceHolder.instance;
    }

    public <E> String getAppProperty(E name, KeysProperties... keys) {
        return properties.getProperty(getPropertiesName(name, keys));
    }

    public <E> int getMaxCountOnArea(GameObjectName objectName) {
        return Integer.parseInt(properties
                .getProperty(getPropertiesName(objectName, MAX, COUNT)));
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
        public static FindAppProperties instance = new FindAppProperties();
    }
}
