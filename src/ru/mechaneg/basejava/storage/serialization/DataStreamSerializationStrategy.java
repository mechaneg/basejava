package ru.mechaneg.basejava.storage.serialization;

import ru.mechaneg.basejava.exception.ResumeSerializationError;
import ru.mechaneg.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializationStrategy implements ISerializationStrategy {
    @Override
    public void serialize(Resume resume, OutputStream stream) throws IOException {
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

                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        write((MarkedTextSection) section, dos);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        write((OrganizationSection) section, dos);
                }
            }

        }
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
            writeStringEmptyIfNull(org.getCompanyUrl(), dos);

            // Serialize positions
            //
            dos.writeInt(org.getPositions().size());
            for (Position pos : org.getPositions()) {
                write(pos, dos);
            }
        }
    }

    private void write(Position position, DataOutputStream dos) throws IOException {
        writeStringEmptyIfNull(position.getTitle(), dos);
        writeStringEmptyIfNull(position.getDescription(), dos);
        write(position.getStart(), dos);
        write(position.getEnd(), dos);
    }

    private void write(LocalDate date, DataOutputStream dos) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
        dos.writeInt(date.getDayOfMonth());
    }

    private void writeStringEmptyIfNull(String string, DataOutputStream dos) throws IOException {
        if (string == null) {
            dos.writeUTF("");
        } else {
            dos.writeUTF(string);
        }
    }

    @Override
    public Resume deserialize(InputStream stream) throws IOException {
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

                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.setSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.setSection(sectionType, readMarkedTextSection(dis));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.setSection(sectionType, readOrganizationSection(dis));
                }
            }

            return resume;
        }
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
        String companyUrl = readStringNullIfEmpty(dis);

        List<Position> positions = new ArrayList<>();
        int positionsSize = dis.readInt();
        for (int i = 0; i < positionsSize; i++) {
            positions.add(readPosition(dis));
        }

        return new Organization(company, companyUrl, positions);
    }

    private Position readPosition(DataInputStream dis) throws IOException {
        return new Position(
                readStringNullIfEmpty(dis),
                readStringNullIfEmpty(dis),
                readDate(dis),
                readDate(dis)
        );
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }

    private String readStringNullIfEmpty(DataInputStream dis) throws IOException {
        String string = dis.readUTF();
        if (string.equals("")) {
            return null;
        }
        return string;
    }
}
