package ru.mechaneg.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Education {
    private String organization;
    private String organizationUrl;
    private String description;
    private LocalDate start;
    private LocalDate end;

    public Education(String organization, String organizationUrl, String description, LocalDate start, LocalDate end) {
        this.organization = organization;
        this.organizationUrl = organizationUrl;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    public String getOrganization() {
        return organization;
    }

    public String getOrganizationUrl() {
        return organizationUrl;
    }

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
        return "Education{" +
                "organization='" + organization + '\'' +
                ", organizationUrl='" + organizationUrl + '\'' +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Education education = (Education) o;
        return Objects.equals(organization, education.organization) &&
                Objects.equals(organizationUrl, education.organizationUrl) &&
                Objects.equals(description, education.description) &&
                Objects.equals(start, education.start) &&
                Objects.equals(end, education.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organization, organizationUrl, description, start, end);
    }
}
