package ru.mechaneg.basejava.model;

public enum ContactType {
    TELEPHONE ("Тел."),
    SKYPE ("Skype"),
    MAIL ("Почта"),
    LINKEDIN ("Профиль LinkedIn"),
    GITHUB ("Профиль GitHub"),
    STACKOVERFLOW ("Профиль Stackoverflow"),
    HOMEPAGE ("Домашняя страница");

    private final String name;

    ContactType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
