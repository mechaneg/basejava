package ru.mechaneg.basejava.storage.serialization;

import ru.mechaneg.basejava.model.*;
import ru.mechaneg.basejava.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlSerializationStrategy implements ISerializationStrategy {

    private XmlParser xmlParser;

    public XmlSerializationStrategy() {
        xmlParser = new XmlParser(Resume.class, Organization.class, Position.class, Contact.class,
                OrganizationSection.class, MarkedTextSection.class, TextSection.class);
    }

    @Override
    public void serialize(Resume resume, OutputStream stream) throws IOException {
        try (Writer writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }

    }

    @Override
    public Resume deserialize(InputStream stream) throws IOException {
        try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
