package ru.mechaneg.basejava.model;

import ru.mechaneg.basejava.exception.ResumeContactNotExistException;
import ru.mechaneg.basejava.exception.ResumeSectionNotExistException;

import java.util.EnumMap;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {

    private String uuid;
    private String fullName;
    private EnumMap<ContactType, Contact> contacts = new EnumMap<>(ContactType.class);
    private EnumMap<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);


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

    public Contact getContact(ContactType type) {
        if (contacts.containsKey(type)) {
            return contacts.get(type);
        }
        throw new ResumeContactNotExistException();
    }

    public void setContact(ContactType type, Contact contact) {
        contacts.put(type, contact);
    }

    public AbstractSection getSection(SectionType type) {
        if (sections.containsKey(type)) {
            return sections.get(type);
        }
        throw new ResumeSectionNotExistException();
    }

    public void setSection(SectionType type, AbstractSection section) {
        sections.put(type, section);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contacts=" + contacts +
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
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }
}
