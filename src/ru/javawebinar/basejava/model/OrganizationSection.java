package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection implements Section {
    private final List<Organization> organizationList;

    public OrganizationSection(List<Organization> organizations) {
        this.organizationList = organizations;
    }

    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return Objects.equals(organizationList, that.organizationList);
    }

    @Override
    public int hashCode() {
        return organizationList != null ? organizationList.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "organizations " + organizationList.toString();
    }
}
