package vika.kovalenko.app.core.utility;

import lombok.Getter;
import org.yaml.snakeyaml.Yaml;
import vika.kovalenko.app.core.constants.GameObjectName;
import vika.kovalenko.app.core.constants.KeyProperty;
import vika.kovalenko.app.core.exceptions.FileProcessingException;
import vika.kovalenko.app.core.utility.util.MathUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static vika.kovalenko.app.core.constants.KeyProperty.COUNT;
import static vika.kovalenko.app.core.constants.KeyProperty.MAX;
import static vika.kovalenko.app.primary.manage.constants.ConfigFilePath.APP_PROPERTY;
import static vika.kovalenko.app.primary.manage.constants.ConfigFilePath.RATION;

public class GameSettings {
    @Getter
    private static final GameSettings INSTANCE = new GameSettings();
    @Getter
    private final Map<GameObjectName, Map<GameObjectName, Integer>> ANIMALS_RATION;
    private final Properties PROPERTIES;

    {
        PROPERTIES = new Properties();
        Yaml yaml = new Yaml();
        Map<String, Map<String, Integer>> rations;

        try (var propertyFile = getResource(APP_PROPERTY); var rationFile = getResource(RATION)) {
            PROPERTIES.load(propertyFile);
            rations = yaml.load(rationFile);
        } catch (IOException ex) {
            throw new FileProcessingException("Failed to read configuration file.", ex);
        }
        ANIMALS_RATION = Converter.mapToGameObjectNames(rations);
    }

    private InputStream getResource(String fileName) {
        return GameSettings.class.getClassLoader().getResourceAsStream(fileName);
    }

    private GameSettings() {
    }

    public <E> String getAppProperty(E name, KeyProperty... keys) {
        return PROPERTIES.getProperty(getPropertiesName(name, keys));
    }

    private <E> String getPropertiesName(E name, KeyProperty... keys) {
        String dot = ".";
        var propertiesName = new StringBuilder();
        propertiesName.append(name).append(dot);

        int totalKeys = keys.length;
        int lastKeyIndex = totalKeys - 1;

        for (int i = 0; i < totalKeys; i++) {
            propertiesName.append(keys[i]);
            if (i < lastKeyIndex) {
                propertiesName.append(dot);
            }
        }

        return propertiesName.toString().toLowerCase();
    }

    public int getMaxCountOnArea(GameObjectName objectName) {
        return MathUtil.toInt(getAppProperty(objectName, MAX, COUNT));
    }

    private static class Converter {
        static Map<GameObjectName, Map<GameObjectName, Integer>> mapToGameObjectNames(Map<String, Map<String, Integer>> map) {
            return
                    map
                            .entrySet()
                            .stream()
                            .collect(Collectors
                                    .toMap(entry -> convertStringToObjectName(entry.getKey()),
                                            entry -> convertInnerMap(entry.getValue())));
        }

        private static GameObjectName convertStringToObjectName(String target) {
            return GameObjectName.valueOf(target.toUpperCase());
        }

        private static Map<GameObjectName, Integer> convertInnerMap(Map<String, Integer> map) {
            return map
                    .entrySet()
                    .stream()
                    .collect(Collectors
                            .toMap(entry -> convertStringToObjectName(entry.getKey()),
                                    Map.Entry::getValue));
        }
    }
}
