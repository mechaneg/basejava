package ru.mechaneg.basejava.model;

import java.util.List;
import java.util.Objects;

public class ExperienceSection extends AbstractSection {
    private List<Experience> experiences;

    public ExperienceSection(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperienceSection that = (ExperienceSection) o;
        return Objects.equals(experiences, that.experiences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(experiences);
    }
}
