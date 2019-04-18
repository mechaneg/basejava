package ru.mechaneg.basejava.model;

public class MailContact extends AbstractContact {
    private String address;

    public MailContact(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "MailContact{" +
                "address='" + address + '\'' +
                '}';
    }
}
