package ru.mechaneg.basejava.model;

import ru.mechaneg.basejava.util.DataSerializable;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.Serializable;

public abstract class AbstractSection implements Serializable, DataSerializable {
    private static final long serialVersionUID = 1;

    @Override
    public AbstractSection read(DataInputStream dis) throws IOException {
        return null;
    }
}
