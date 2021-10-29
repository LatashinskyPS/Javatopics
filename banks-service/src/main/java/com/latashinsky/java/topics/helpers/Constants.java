package com.latashinsky.java.topics.helpers;

import com.latashinsky.java.topics.services.MessageService;

import java.util.Objects;
import java.util.Properties;

public final class Constants {
    public final static String ROOT_PATH = Objects.requireNonNull(
            Thread.currentThread().getContextClassLoader().getResource("")).getPath();
    private static final String appConfigPath = Constants.ROOT_PATH + "java-topic-configurations.properties";
    public static Properties properties = new Properties();
    public final static String PATTERN_DOUBLE = "^[0-9]*[.]?[0-9]+$";
    public final static String PATTERN_INT = "[0-9]+";
    public final static String PATTERN_DATE = "^\\d{4}-\\d{2}-\\d{2}$";
    public static String PATH;
    public static String PATH_TO_DATA;
    public static String PROFILE;
    public static MessageService messageService;
}
