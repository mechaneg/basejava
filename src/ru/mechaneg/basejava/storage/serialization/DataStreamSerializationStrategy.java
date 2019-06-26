package ru.mechaneg.basejava.storage.serialization;

import ru.mechaneg.basejava.exception.ResumeSerializationError;
import ru.mechaneg.basejava.model.*;
import ru.mechaneg.basejava.util.Pair;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                dos.writeUTF(contact.getValue());
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

            Map<ContactType, String> contacts = readListWithException(dis, () -> readTypedContact(dis))
                    .stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

            Map<SectionType, AbstractSection> sections = readListWithException(dis, () -> readTypedSection(dis))
                    .stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

            return new Resume(uuid, fullName, contacts, sections);
        }
    }

    private MarkedTextSection readMarkedTextSection(DataInputStream dis) throws IOException {
        return new MarkedTextSection(readListWithException(dis, dis::readUTF));
    }

    private OrganizationSection readOrganizationSection(DataInputStream dis) throws IOException {
        return new OrganizationSection(readListWithException(dis, () -> readOrganization(dis)));
    }

    private Organization readOrganization(DataInputStream dis) throws IOException {
        return new Organization(
                dis.readUTF(),
                readStringNullIfEmpty(dis),
                readListWithException(dis, () -> readPosition(dis))
        );
    }

    private Pair<ContactType, String> readTypedContact(DataInputStream dis) throws IOException {
        return new Pair<>(ContactType.valueOf(dis.readUTF()), dis.readUTF());
    }

    private Pair<SectionType, AbstractSection> readTypedSection(DataInputStream dis) throws IOException {
        SectionType sectionType = SectionType.valueOf(dis.readUTF());

        AbstractSection section;
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                section = new TextSection(dis.readUTF());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                section = readMarkedTextSection(dis);
                break;
            case EXPERIENCE:
            case EDUCATION:
                section = readOrganizationSection(dis);
                break;
            default:
                throw new ResumeSerializationError("Unknown section type" + sectionType);
        }

        return new Pair<>(sectionType, section);
    }

    private <T> List<T> readListWithException(DataInputStream dis, DataInputStreamReader<T> reader) throws IOException {
        List<T> list = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            list.add(reader.accept());
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

    @FunctionalInterface
    private interface DataOutputStreamWriter<T> {
        void accept(T t) throws IOException;
    }

    @FunctionalInterface
    private interface DataInputStreamReader<T> {
        T accept() throws IOException;
    }
}
