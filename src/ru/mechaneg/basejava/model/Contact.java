package ru.mechaneg.basejava.model;

import ru.mechaneg.basejava.util.DataSerializable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public class Contact implements Serializable, DataSerializable {
    private static final long serialVersionUID = 1;

    private String value;

    public Contact() {
    }

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

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(value);
    }

    @Override
    public Contact read(DataInputStream dis) throws IOException {
        return new Contact(dis.readUTF());
    }
}
