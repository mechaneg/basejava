package ru.mechaneg.basejava.util;

import com.google.gson.*;
import ru.mechaneg.basejava.exception.JsonParserException;

import java.lang.reflect.Type;

public class JsonSectionAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    private static String CLASSNAME = "CLASSNAME";
    private static String INSTANCE = "INSTANCE";

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String className = jsonObject.getAsJsonPrimitive(CLASSNAME).getAsString();

        try {
            return context.deserialize(jsonObject.get(INSTANCE), Class.forName(className));
        } catch (ClassNotFoundException ex) {
            throw new JsonParserException("Unable to deserialize Secton class", ex);
        }
    }

    @Override
    public JsonElement serialize(T section, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();

        retValue.addProperty(CLASSNAME, section.getClass().getName());
        retValue.add(INSTANCE, context.serialize(section));

        return retValue;
    }
}
