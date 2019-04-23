package ru.mechaneg.basejava.model;

import java.util.List;
import java.util.Objects;

public class Organization {

    private final String company;
    private final String companyUrl;
    private final List<Position> positions;

    public Organization(String company, String companyUrl, List<Position> positions) {
        this.company = company;
        this.companyUrl = companyUrl;
        this.positions = positions;
    }

    public String getCompany() {
        return company;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(company, that.company) &&
                Objects.equals(companyUrl, that.companyUrl) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, companyUrl, positions);
    }
}
