package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.EventPeriod;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.TextListSection;

import java.time.format.DateTimeFormatter;

public class HtmlUtil {
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public HtmlUtil() {
    }

    public static String getStartDateFormat(EventPeriod eventPeriod) {
        return eventPeriod.getStartDate().format(FORMATTER);
    }

    public static String getEndDateFormat(EventPeriod eventPeriod) {
        return eventPeriod.getEndDate().format(FORMATTER);
    }

    public static String textListSectionToList(Section section) {
        if (section != null) {
            return String.join("\n", ((TextListSection) section).
                    getTextList().toArray((new String[0]))
            );
        } else return "";
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
