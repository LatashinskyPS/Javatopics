package by.latashinsky.java.topics.entities;

public enum UserTypes {
    USUAL("Usual"), LEGAL("Legal");

    public String getValue() {
        return value;
    }

    private final String value;

    UserTypes(String value) {
        this.value = value;
    }

}
