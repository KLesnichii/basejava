package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private final String header;
    private final String link;
    private final List<Period> periodList;

    public Organization(String header, String link, List<Period> periodList) {
        this.header = Objects.requireNonNull(header, "header must not be null");
        this.link = link;
        this.periodList = Objects.requireNonNull(periodList, "periodList must not be null");
    }

    public String getHeader() {
        return header;
    }

    public String getLink() {
        return link;
    }

    public List<Period> getPeriodList() {
        return periodList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!header.equals(that.header)) return false;
        if (!Objects.equals(link, that.link)) return false;
        return periodList.equals(that.periodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, link, periodList);
    }

    @Override
    public String toString() {
        return "header " + header +
                " link " + link +
                " periodList " + periodList.toString();
    }
}
