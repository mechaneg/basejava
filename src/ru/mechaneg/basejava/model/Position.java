package ru.mechaneg.basejava.model;

import ru.mechaneg.basejava.exception.InconsistentDatePeriodException;
import ru.mechaneg.basejava.util.DataSerializable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Position implements Serializable, DataSerializable {
    private static final long serialVersionUID = 1;

    private String position;
    private String description;
    private LocalDate start;
    private LocalDate end;

    public Position() {
    }

    public Position(String position, String description, LocalDate start, LocalDate end) {
        this.position = position;
        this.description = description;
        this.start = start;
        this.end = end;

        if (start.compareTo(end) >= 0) {
            throw new InconsistentDatePeriodException();
        }
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position entry = (Position) o;
        return Objects.equals(position, entry.position) &&
                Objects.equals(description, entry.description) &&
                Objects.equals(start, entry.start) &&
                Objects.equals(end, entry.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, description, start, end);
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(position);
        dos.writeUTF(description);

        class DateWriter {
            void write(LocalDate date, DataOutputStream dos) throws IOException {
                dos.writeInt(date.getYear());
                dos.writeInt(date.getMonthValue());
                dos.writeInt(date.getDayOfMonth());
            }
        }

        DateWriter dateWriter = new DateWriter();
        dateWriter.write(start, dos);
        dateWriter.write(end, dos);
    }

    @Override
    public Position read(DataInputStream dis) throws IOException {

        class DateReader {
            LocalDate read(DataInputStream dis) throws IOException {
                return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
            }
        }

        DateReader dateReader = new DateReader();

        return new Position(
                dis.readUTF(),
                dis.readUTF(),
                dateReader.read(dis),
                dateReader.read(dis)
        );
    }
}
