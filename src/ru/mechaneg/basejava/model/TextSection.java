package ru.mechaneg.basejava.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class TextSection extends AbstractSection {
    private static final long serialVersionUID = 1;

    private final String content;

    public TextSection(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(getClass().getName());
        dos.writeUTF(content);
    }

    @Override
    public AbstractSection read(DataInputStream dis) throws IOException {
        return new TextSection(dis.readUTF());
    }
}
