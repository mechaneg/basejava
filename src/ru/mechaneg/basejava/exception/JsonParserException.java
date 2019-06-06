package ru.mechaneg.basejava.exception;

public class JsonParserException extends RuntimeException {
    public JsonParserException(String s) { super(s); }

    public JsonParserException(String s, Exception ex) { super(s, ex); }
}
