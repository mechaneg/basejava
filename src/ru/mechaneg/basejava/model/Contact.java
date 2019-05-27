package ru.mechaneg.basejava.model;

import java.io.Serializable;
import java.util.Objects;

public class Contact implements Serializable {
    private static final long serialVersionUID = -8755221053452248832L;

    private final String value;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(value, contact.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
