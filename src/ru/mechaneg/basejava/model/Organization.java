package ru.mechaneg.basejava.model;

import java.util.List;
import java.util.Objects;

public class Organization {

    private final String company;
    private final String companyUrl;
    private final List<OrganizationEntry> entries;

    public Organization(String company, String companyUrl, List<OrganizationEntry> entries) {
        this.company = company;
        this.companyUrl = companyUrl;
        this.entries = entries;
    }

    public String getCompany() {
        return company;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public List<OrganizationEntry> getEntries() {
        return entries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(company, that.company) &&
                Objects.equals(companyUrl, that.companyUrl) &&
                Objects.equals(entries, that.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, companyUrl, entries);
    }
}
