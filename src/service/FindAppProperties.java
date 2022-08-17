package service;

import resources.KeysProperties;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FindAppProperties {
    private static final Properties properties;
    private static FileReader reader;

    static {
        properties = new Properties();
        String propertiesPath = "src/resources/app.properties";
        try {
            reader = new FileReader(propertiesPath);
        } catch (FileNotFoundException e) {
            //TODO add normal exception
            e.printStackTrace();
        }
    }

    private FindAppProperties() {
    }

    public static String getAppProperty(KeysProperties... keys) {
        try {
            properties.load(reader);
        } catch (IOException e) {
            //TODO add normal exception
            e.printStackTrace();
        }
        return properties.getProperty(getPropertiesName(keys));
    }

    private static String getPropertiesName(KeysProperties... keys) {
        var propertiesName = new StringBuilder();
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
}
