package vika.kovalenko.app.primary.manage.util;

import lombok.Getter;
import vika.kovalenko.app.core.exceptions.ReaderException;
import vika.kovalenko.app.core.exceptions.UserInputException;
import vika.kovalenko.app.core.utility.util.MathUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static vika.kovalenko.app.primary.manage.constants.Messages.WRITE_NUMBER;

public class ReaderUtil {
    private static final String ERROR_READING_INPUT = "BufferedReader reading input error";
    private static final String READER_CLOSE_ERROR = "BufferedReader closing error";
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    @Getter
    private static boolean readerOpen = true;

    private ReaderUtil() {
    }

    public static String readLine() {
        try {
            return READER.readLine();
        } catch (IOException ex) {
            throw new ReaderException(ERROR_READING_INPUT, ex);
        }
    }

    public static int readNumber() {
        try {
            return MathUtil.toInt(readLine());
        } catch (NumberFormatException ex) {
            throw new UserInputException(WRITE_NUMBER);
        }
    }

    public static void cleanReaderBuffer() {
        try {
            while (READER.ready()) {
                ReaderUtil.readLine();
            }
        } catch (IOException ex) {
            throw new ReaderException(ERROR_READING_INPUT, ex);
        }
    }

    public static void closeReader() {
        try {
            READER.close();
            readerOpen = false;
        } catch (IOException ex) {
            throw new ReaderException(READER_CLOSE_ERROR, ex);
        }
    }
}
