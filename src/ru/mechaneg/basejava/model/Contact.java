package ru.mechaneg.basejava.model;

public class Contact {
    private String value;

    public Contact(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "value='" + value + '\'' +
                '}';
    }
}
