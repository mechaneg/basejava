package ru.mechaneg.basejava.model;

import ru.mechaneg.basejava.exception.InconsistentDatePeriodException;

import java.time.LocalDate;
import java.util.Objects;

public class Position {
    private final String position;
    private final String description;
    private final LocalDate start;
    private final LocalDate end;

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
}
