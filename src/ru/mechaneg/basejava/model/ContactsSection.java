package ru.mechaneg.basejava.model;

import java.util.EnumMap;
import java.util.Objects;

public class ContactsSection extends AbstractSection {

    private EnumMap<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    public ContactsSection() {
    }

    public ContactsSection(EnumMap<ContactType, String> contacts) {
        this.contacts = contacts;
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public void setContact(ContactType type, String contact) {
        contacts.put(type, contact);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactsSection that = (ContactsSection) o;
        return Objects.equals(contacts, that.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contacts);
    }

    @Override
    public String toString() {
        return "ContactsSection{" +
                "contacts=" + contacts +
                '}';
    }
}
