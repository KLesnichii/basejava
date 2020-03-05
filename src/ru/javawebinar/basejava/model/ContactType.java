package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE_NUMBER("Телефонный номер"),
    SKYPE("Skype"),
    EMAIL("Почта"),
    GITHUB("GitHub"),
    HOME_PAGE("Домашняя страница"),
    OTHER_PAGE("Дополнительная информация");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
