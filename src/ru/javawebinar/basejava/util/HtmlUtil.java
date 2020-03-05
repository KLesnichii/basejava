package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.EventPeriod;

import java.time.format.DateTimeFormatter;

public class HtmlUtil {

    public HtmlUtil() {
    }

    public static String getStartDateFormat(EventPeriod eventPeriod) {
        return eventPeriod.getStartDate().format(DateTimeFormatter.ofPattern("MM/yyyy"));
    }

    public static String getEndDateFormat(EventPeriod eventPeriod) {
        return eventPeriod.getEndDate().format(DateTimeFormatter.ofPattern("MM/yyyy"));
    }

    public static String toHtml(ContactType type, String value) {
        return (value == null) ? "" : toHtml0(type, value);
    }

    private static String toHtml0(ContactType type, String value) {
        switch (type) {
            case EMAIL:
                return type.getTitle() + ": " + toLink("mailto:" + value, value);
            case SKYPE:
                return type.getTitle() + ": " + toLink("skype:" + value, value);
            case GITHUB:
            case HOME_PAGE:
            case OTHER_PAGE:
                return toLink(value, type.getTitle());
            case PHONE_NUMBER:
                return type.getTitle() + ": " + value;
        }
        return type.getTitle() + ": " + value;
    }

    private static String toLink(String href, String title) {
        return "<a href='" + href + "' title='" + title + "'>" + title + "</a>";
    }
}
