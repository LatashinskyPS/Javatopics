package com.latashinsky.banksservice.helpers;

import java.util.Collection;

public class CollectionConverter {
    public static <T> String convert(Collection<T> list) {
        StringBuilder stringBuilder = new StringBuilder();
        list.stream().distinct().forEach(r -> stringBuilder.append(r.toString()).append("\n"));
        return stringBuilder.toString();
    }
}
