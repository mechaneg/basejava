package ru.mechaneg.basejava.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface DataSerializable {
    void write(DataOutputStream dos) throws IOException;
    DataSerializable read(DataInputStream dis) throws IOException;
}
