package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final String header;
    private final String link;
    private final List<Period> periodList;

    public Organization(String header, String link, List<Period> periodList) {
        this.header = header;
        this.link = link;
        this.periodList = periodList;
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

        if (!Objects.equals(header, that.header)) return false;
        if (!Objects.equals(link, that.link)) return false;
        return Objects.equals(periodList, that.periodList);
    }

    @Override
    public int hashCode() {
        int result = header != null ? header.hashCode() : 0;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (periodList != null ? periodList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "header " + header +
                " link " + link +
                " periodList " + periodList.toString();
    }
}
