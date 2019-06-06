package ru.mechaneg.basejava.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.mechaneg.basejava.model.AbstractSection;

import java.io.Reader;
import java.io.Writer;

public class JsonParser {
    private static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(AbstractSection.class, new JsonSectionAdapter())
            .create();

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }

    public static <T> T read(Reader reader, Class<T> classType) {
        return GSON.fromJson(reader, classType);
    }
}
