package ru.mechaneg.basejava.model;

import ru.mechaneg.basejava.exception.ResumeSectionNotExistException;
import ru.mechaneg.basejava.exception.ResumeSectionTypeMismatchException;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume {

    private String uuid;
    private String fullName;

    public enum SectionType {
        PERSONAL,
        OBJECTIVE,
        ACHIEVEMENT,
        QUALIFICATIONS,
        EXPERIENCE,
        EDUCATION,
        CONTACTS;

    }
    private Map<SectionType, AbstractSection> sections = new HashMap<>();


    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String newUuid) {
        uuid = newUuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public AbstractSection getSection(SectionType type) {
        if (sections.containsKey(type)) {
            return sections.get(type);
        }
        throw new ResumeSectionNotExistException();
    }

    public void setSection(SectionType type, AbstractSection section) {
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                if (!(section instanceof TextSection)) {
                    throw new ResumeSectionTypeMismatchException();
                }
                break;
            case EXPERIENCE:
            case EDUCATION:
                if (!(section instanceof ChronologicalSection)) {
                    throw new ResumeSectionTypeMismatchException();
                }
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                if (!(section instanceof EnumeratedTextSection)) {
                        throw new ResumeSectionTypeMismatchException();
                }
                break;
            case CONTACTS:
                if (!(section instanceof ContactsSection)) {
                    throw new ResumeSectionTypeMismatchException();
                }
                break;
        }
        sections.put(type, section);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", sections=" + sections +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, sections);
    }
}
