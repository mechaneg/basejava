package ru.mechaneg.basejava.model;

import java.util.HashMap;
import java.util.Map;

public class ContactsSection extends AbstractSection {

    public enum ContactType {
        TELEPHONE,
        SKYPE,
        MAIL,
        LINKEDIN,
        GITHUB,
        STACKOVERFLOW,
        HOMEPAGE
    }

    private Map<ContactType, String> contacts = new HashMap<>();

    public ContactsSection() {
    }

    public ContactsSection(Map<ContactType, String> contacts) {
        this.contacts = contacts;
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public void setContact(ContactType type, String contact) {
        contacts.put(type, contact);
    }

    @Override
    public String toString() {
        return "ContactsSection{" +
                "contacts=" + contacts +
                '}';
    }
}
