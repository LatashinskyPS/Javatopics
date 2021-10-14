package by.latashinsky.java.topics.models;

import java.util.Collection;

public class MyListConverter {
    public static <T> String convert(Collection<T> list) {
        StringBuilder stringBuilder = new StringBuilder();
        list.stream().distinct().forEach(r -> stringBuilder.append(r.toString()).append("\n"));
        return stringBuilder.toString();
    }
}
