package by.latashinsky.java.topics.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public final class Constants {
    public final static String ROOT_PATH = Objects.requireNonNull(
            Thread.currentThread().getContextClassLoader().getResource("")).getPath();
    private static final String appConfigPath = Constants.ROOT_PATH + "java-topic-configurations.properties";
    public static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public final static String PATTERN_DOUBLE = "^[0-9]*[.]?[0-9]+$";
    public final static String PATTERN_INT = "[0-9]+";
    public final static String PATTERN_DATE = "^\\d{4}-\\d{2}-\\d{2}$";
    public final static String PATH = properties.getProperty("path");
}
