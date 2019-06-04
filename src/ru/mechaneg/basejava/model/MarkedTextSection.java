package ru.mechaneg.basejava.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MarkedTextSection extends AbstractSection {
    private static final long serialVersionUID = 1;

    private final List<String> items;

    public MarkedTextSection(List<String> items) {
        Objects.requireNonNull(items);
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkedTextSection that = (MarkedTextSection) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(getClass().getName());
        dos.writeInt(items.size());
        for (String item : items) {
            dos.writeUTF(item);
        }
    }

    @Override
    public AbstractSection read(DataInputStream dis) throws IOException {
        int size = dis.readInt();

        List<String> items = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            items.add(dis.readUTF());
        }

        return new MarkedTextSection(items);
    }
}
