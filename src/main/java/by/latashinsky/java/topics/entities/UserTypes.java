package by.latashinsky.java.topics.entities;

import java.util.Locale;

public enum UserTypes {
    USUAL("Usual"), LEGAL("Legal");

    public static UserTypes getUsertype(String type) {
        switch (type.toUpperCase(Locale.ROOT)){
            case "USUAL" :{
                return USUAL;
            }
            case "LEGAL":{
                return LEGAL;
            }
            default:{
                return null;
            }
        }
    }

    public String getValue() {
        return value;
    }

    private final String value;

    UserTypes(String value) {
        this.value = value;
    }

}
