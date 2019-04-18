package ru.mechaneg.basejava.model;

public class VoipContact extends AbstractContact {
    private String login;

    public VoipContact(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return "VoipContact{" +
                "login='" + login + '\'' +
                '}';
    }
}
