package com.latashinsky.java.topics.entities;

import java.util.Locale;

public enum UserTypes {
    USUAL, LEGAL;

    public static UserTypes getUserType(String type) {
        switch (type.toUpperCase(Locale.ROOT)) {
            case "USUAL": {
                return USUAL;
            }
            case "LEGAL": {
                return LEGAL;
            }
            default: {
                return null;
            }
        }
    }


}
