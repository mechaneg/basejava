package ru.mechaneg.basejava.model;

import ru.mechaneg.basejava.exception.InconsistentDatePeriodException;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {
    private final String company;
    private final String companyUrl;
    private final String position;
    private final String description;
    private final LocalDate start;
    private final LocalDate end;

    public Organization(String company, String companyUrl, String position, String description, LocalDate start, LocalDate end) {
        this.company = company;
        this.companyUrl = companyUrl;
        this.position = position;
        this.description = description;
        this.start = start;
        this.end = end;

        if (start.compareTo(end) >= 0) {
            throw new InconsistentDatePeriodException();
        }
    }

    public String getCompany() {
        return company;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public String getPosition() { return position; }

    public String getDescription() {
        return description;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "company='" + company + '\'' +
                ", companyUrl='" + companyUrl + '\'' +
                ", position='" + position + '\'' +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(company, that.company) &&
                Objects.equals(companyUrl, that.companyUrl) &&
                Objects.equals(position, that.position) &&
                Objects.equals(description, that.description) &&
                Objects.equals(start, that.start) &&
                Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, companyUrl, position, description, start, end);
    }
}
