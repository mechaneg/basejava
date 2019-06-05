package ru.mechaneg.basejava.storage.serialization;

import ru.mechaneg.basejava.exception.ResumeSerializationError;
import ru.mechaneg.basejava.exception.StorageException;
import ru.mechaneg.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializationStrategy implements ISerializationStrategy {
    @Override
    public void serialize(Resume resume, OutputStream stream) {
        try (DataOutputStream dos = new DataOutputStream(stream)) {

            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            // Serializing contacts
            //
            dos.writeInt(resume.getContacts().size());
            for (Map.Entry<ContactType, Contact> entry : resume.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue().getValue());
            }

            // Serializing sections
            //
            dos.writeInt(resume.getSections().size());
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                dos.writeUTF(entry.getKey().toString());

                AbstractSection section = entry.getValue();
                dos.writeUTF(section.getClass().getName());

                if (section instanceof TextSection) {
                    write((TextSection) section, dos);
                } else if (section instanceof MarkedTextSection) {
                    write((MarkedTextSection) section, dos);
                } else if (section instanceof OrganizationSection) {
                    write((OrganizationSection) section, dos);
                } else {
                    throw new ResumeSerializationError("Unexpected child of AbstractSection class");
                }
            }

        } catch (IOException ex) {
            throw new StorageException("Unable to create data output stream", resume.getUuid(), ex);
        }
    }

    private void write(TextSection section, DataOutputStream dos) throws IOException {
        dos.writeUTF(section.getContent());
    }

    private void write(MarkedTextSection section, DataOutputStream dos) throws IOException {
        dos.writeInt(section.getItems().size());
        for (String item : section.getItems()) {
            dos.writeUTF(item);
        }
    }

    private void write(OrganizationSection section, DataOutputStream dos) throws IOException {
        dos.writeInt(section.getOrganizations().size());
        for (Organization org : section.getOrganizations()) {
            dos.writeUTF(org.getCompany());
            dos.writeUTF(org.getCompanyUrl());

            // Serialize positions
            //
            dos.writeInt(org.getPositions().size());
            for (Position pos : org.getPositions()) {
                write(pos, dos);
            }
        }
    }

    private void write(Position position, DataOutputStream dos) throws IOException {
        if (position.getPosition() == null) {
            dos.writeBoolean(false);
        } else {
            dos.writeBoolean(true);
            dos.writeUTF(position.getPosition());
        }

        dos.writeUTF(position.getDescription());

        write(position.getStart(), dos);
        write(position.getEnd(), dos);
    }

    private void write(LocalDate date, DataOutputStream dos) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
        dos.writeInt(date.getDayOfMonth());
    }

    @Override
    public Resume deserialize(InputStream stream) {
        try (DataInputStream dis = new DataInputStream(stream)) {

            String uuid = dis.readUTF();
            String fullName = dis.readUTF();

            Resume resume = new Resume(uuid, fullName);

            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                resume.setContact(ContactType.valueOf(dis.readUTF()), new Contact(dis.readUTF()));
            }

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());

                AbstractSection section;
                String sectionClassName = dis.readUTF();
                if (sectionClassName.equals(TextSection.class.getName())) {
                    section = readTextSection(dis);
                } else if (sectionClassName.equals(MarkedTextSection.class.getName())) {
                    section = readMarkedTextSection(dis);
                } else if (sectionClassName.equals(OrganizationSection.class.getName())) {
                    section = readOrganizationSection(dis);
                } else {
                    throw new ResumeSerializationError("Unknown child of AbstractSection");
                }

                resume.setSection(sectionType, section);
            }

            return resume;
        } catch (IOException ex) {
            throw new ResumeSerializationError("Unable to create data input stream", ex);
        }
    }

    private TextSection readTextSection(DataInputStream dis) throws IOException {
        return new TextSection(dis.readUTF());
    }

    private MarkedTextSection readMarkedTextSection(DataInputStream dis) throws IOException {
        int size = dis.readInt();

        List<String> items = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            items.add(dis.readUTF());
        }

        return new MarkedTextSection(items);
    }

    private OrganizationSection readOrganizationSection(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<Organization> organizations = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            organizations.add(readOrganization(dis));
        }

        return new OrganizationSection(organizations);
    }

    private Organization readOrganization(DataInputStream dis) throws IOException {
        String company = dis.readUTF();
        String companyUrl = dis.readUTF();

        List<Position> positions = new ArrayList<>();
        int positionsSize = dis.readInt();
        for (int i = 0; i < positionsSize; i++) {
            positions.add(readPosition(dis));
        }

        return new Organization(company, companyUrl, positions);
    }

    private Position readPosition(DataInputStream dis) throws IOException {
        String position = null;
        if (dis.readBoolean()) {
            position = dis.readUTF();
        }

        return new Position(
                position,
                dis.readUTF(),
                readDate(dis),
                readDate(dis)
        );
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }
}
