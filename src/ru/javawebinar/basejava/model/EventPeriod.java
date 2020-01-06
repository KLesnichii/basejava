package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class EventPeriod implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate startDate;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate endDate;
    private String text;

    public EventPeriod() {
    }

    public EventPeriod(String title, LocalDate startDate, LocalDate endDate, String text) {
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

        EventPeriod eventPeriod = (EventPeriod) o;

        if (!title.equals(eventPeriod.title)) return false;
        if (!startDate.equals(eventPeriod.startDate)) return false;
        if (!endDate.equals(eventPeriod.endDate)) return false;
        return Objects.equals(text, eventPeriod.text);
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
