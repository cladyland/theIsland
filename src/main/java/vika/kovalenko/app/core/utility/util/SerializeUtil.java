package vika.kovalenko.app.core.utility.util;

import vika.kovalenko.app.core.exceptions.SerializeException;
import vika.kovalenko.app.primary.manage.constants.ConfigFilePath;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.nonNull;

public final class SerializeUtil {
    private static final String ERROR_MESSAGE = "Error while %s the game: ";
    private static final String USER_FILES_DIR = "myIsland/";
    private static final String WORK_DIR = "user.dir";

    public static void serialize(String fileName, Object target) {
        try (var outputStream = new ObjectOutputStream(new FileOutputStream(getFile(fileName)))) {
            outputStream.writeObject(target);
        } catch (IOException ex) {
            throw new SerializeException(addArgToErrorMessage("saving"), ex);
        }
    }

    private static File getFile(String fileName) {
        String relativePath = USER_FILES_DIR + fileName;
        Path absolutePath = Paths.get(System.getProperty(WORK_DIR), relativePath);

        try {
            Path parentDirectory = absolutePath.getParent();
            if (nonNull(parentDirectory)) {
                java.nio.file.Files.createDirectories(parentDirectory);
            }
            return absolutePath.toFile();
        } catch (IOException ex) {
            throw new SerializeException(ex.getMessage(), ex);
        }
    }

    private static String addArgToErrorMessage(String arg) {
        return String.format(ERROR_MESSAGE, arg);
    }

    public static Object deserialize(String fileName) {
        try (var ois = new ObjectInputStream(new FileInputStream(getFile(fileName)))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new SerializeException(addArgToErrorMessage("loading"), ex);
        }
    }

    public static boolean savedFilesExisted() {
        return fileExist(ConfigFilePath.GAME_FIELD_SERIALIZE)
               && fileExist(ConfigFilePath.LIFE_CYCLE_SERIALIZE)
               && fileExist(ConfigFilePath.USER_STATISTIC_SERIALIZE)
               && fileExist(ConfigFilePath.INIT_STATISTIC_SERIALIZE);
    }

    private static boolean fileExist(String filePath) {
        return getFile(filePath).exists();
    }
}
