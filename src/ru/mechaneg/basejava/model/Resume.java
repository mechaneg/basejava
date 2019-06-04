package ru.mechaneg.basejava.model;

import ru.mechaneg.basejava.exception.ResumeContactNotExistException;
import ru.mechaneg.basejava.exception.ResumeDeserializationError;
import ru.mechaneg.basejava.exception.ResumeSectionNotExistException;
import ru.mechaneg.basejava.util.DataSerializable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Serializable, DataSerializable {
    private static final long serialVersionUID = 1;

    private String uuid;
    private String fullName;
    private EnumMap<ContactType, Contact> contacts = new EnumMap<>(ContactType.class);
    private EnumMap<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

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

    public EnumMap<ContactType, Contact> getContacts() {
        return contacts;
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

    public EnumMap<SectionType, AbstractSection> getSections() {
        return sections;
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

    @Override
    public void write(DataOutputStream dos) throws IOException {

        dos.writeUTF(uuid);
        dos.writeUTF(fullName);

        class EnumMapWriter {
            <K extends Enum<K>, V extends DataSerializable>
            void write(EnumMap<K, V> enumMap, DataOutputStream dos) throws IOException {
                dos.writeInt(enumMap.size());
                for (Map.Entry<K, V> entry : enumMap.entrySet()) {
                    dos.writeUTF(entry.getKey().name());
                    entry.getValue().write(dos);
                }
            }
        }

        EnumMapWriter enumMapWriter = new EnumMapWriter();
        enumMapWriter.write(contacts, dos);
        enumMapWriter.write(sections, dos);
    }

    @Override
    public Resume read(DataInputStream dis) throws IOException {
        Resume result = new Resume(dis.readUTF(), dis.readUTF());

        Contact contactFactory = new Contact();

        int contactsSize = dis.readInt();
        for (int i = 0; i < contactsSize; i++) {
            result.setContact(ContactType.valueOf(dis.readUTF()), contactFactory.read(dis));
        }

        try {
            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                AbstractSection sectionFactory = (AbstractSection) Class.forName(dis.readUTF()).newInstance();
                result.setSection(sectionType, sectionFactory.read(dis));
            }

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
            throw new ResumeDeserializationError();
        }

        return result;
    }
}
