package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String header;
    private String link;
    private List<EventPeriod> eventPeriodList;

    public Organization() {
    }

    public Organization(String header, String link, List<EventPeriod> eventPeriodList) {
        this.header = Objects.requireNonNull(header, "header must not be null");
        this.link = link;
        this.eventPeriodList = Objects.requireNonNull(eventPeriodList, "periodList must not be null");
    }

    public String getHeader() {
        return header;
    }

    public String getLink() {
        return link;
    }

    public List<EventPeriod> getEventPeriodList() {
        return eventPeriodList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!header.equals(that.header)) return false;
        if (!Objects.equals(link, that.link)) return false;
        return eventPeriodList.equals(that.eventPeriodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, link, eventPeriodList);
    }

    @Override
    public String toString() {
        return "header " + header +
                " link " + link +
                " periodList " + eventPeriodList.toString();
    }
}
