package ru.mechaneg.basejava.storage.serialization;

import ru.mechaneg.basejava.model.Resume;
import ru.mechaneg.basejava.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonSerializatonStrategy implements ISerializationStrategy {
    @Override
    public void serialize(Resume resume, OutputStream stream) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8)) {
            JsonParser.write(resume, writer);
        }
    }

    @Override
    public Resume deserialize(InputStream stream) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return JsonParser.read(reader, Resume.class);
        }
    }
}
