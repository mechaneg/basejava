package ru.mechaneg.basejava.model;

import java.util.List;
import java.util.Objects;

public class EducationSection extends AbstractSection {
    private List<Education> educations;

    public EducationSection(List<Education> educations) {
        this.educations = educations;
    }

    public List<Education> getEducations() {
        return educations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EducationSection that = (EducationSection) o;
        return Objects.equals(educations, that.educations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(educations);
    }
}
