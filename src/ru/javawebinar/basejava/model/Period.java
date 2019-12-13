package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private final String title;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String text;

    public Period(String title, LocalDate startDate, LocalDate endDate, String text) {
        this.title = Objects.requireNonNull(title, "title must not be null");
        this.startDate = Objects.requireNonNull(startDate, "startDate must not be null");
        this.endDate = Objects.requireNonNull(endDate, "endDate must not be null");
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!title.equals(period.title)) return false;
        if (!startDate.equals(period.startDate)) return false;
        if (!endDate.equals(period.endDate)) return false;
        return Objects.equals(text, period.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, startDate, endDate, text);
    }

    @Override
    public String toString() {
        return "title " + title +
                " startDate " + startDate +
                " endDate " + endDate +
                " text " + text;
    }
}
