package ru.mechaneg.basejava.storage.serialization;

import ru.mechaneg.basejava.exception.ResumeSerializationError;
import ru.mechaneg.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializationStrategy implements ISerializationStrategy {
    @Override
    public void serialize(Resume resume, OutputStream stream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(stream)) {

            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            // Serializing contacts
            //
            writeWithException(resume.getContacts().entrySet(), dos, (contact) -> {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue().getValue());
            });

            // Serializing sections
            //
            writeWithException(resume.getSections().entrySet(), dos, (section) -> {
                dos.writeUTF(section.getKey().toString());

                AbstractSection abstractSection = section.getValue();

                switch (section.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) abstractSection).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        write((MarkedTextSection) abstractSection, dos);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        write((OrganizationSection) abstractSection, dos);
                }
            });
        }
    }

    @FunctionalInterface
    private interface DataOutputStreamWriter<T> {
        void accept(T t) throws IOException;
    }

    private <E> void writeWithException(Collection<E> collection, DataOutputStream dos, DataOutputStreamWriter<E> writer) throws IOException {
        dos.writeInt(collection.size());
        for (E element : collection) {
            writer.accept(element);
        }
    }

    private void write(MarkedTextSection section, DataOutputStream dos) throws IOException {
        writeWithException(section.getItems(), dos, dos::writeUTF);
    }

    private void write(OrganizationSection section, DataOutputStream dos) throws IOException {
        writeWithException(section.getOrganizations(), dos, org -> {

            dos.writeUTF(org.getCompany());
            writeStringEmptyIfNull(org.getCompanyUrl(), dos);

            writeWithException(org.getPositions(), dos, pos -> write(pos, dos));
        });
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
        dos.writeUTF(string == null ? "" : string);
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
        return new MarkedTextSection(readListWithException(dis, DataInput::readUTF));
    }

    private OrganizationSection readOrganizationSection(DataInputStream dis) throws IOException {
        return new OrganizationSection(readListWithException(dis, this::readOrganization));
    }

    private Organization readOrganization(DataInputStream dis) throws IOException {
        return new Organization(
                dis.readUTF(),
                readStringNullIfEmpty(dis),
                readListWithException(dis, this::readPosition)
        );
    }

    @FunctionalInterface
    private interface DataInputStreamReader<T> {
        T accept(DataInputStream dos) throws IOException;
    }

    private <T> List<T> readListWithException(DataInputStream dis, DataInputStreamReader<T> reader) throws IOException {
        List<T> list = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            list.add(reader.accept(dis));
        }
        return list;
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
