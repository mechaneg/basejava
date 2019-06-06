package ru.mechaneg.basejava.model;

import ru.mechaneg.basejava.exception.InconsistentDatePeriodException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Position implements Serializable {
    private static final long serialVersionUID = 1;

    private String title;
    private String description;
    private LocalDate start;
    private LocalDate end;

    public Position(String title, String description, LocalDate start, LocalDate end) {
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;

        Objects.requireNonNull(start);
        Objects.requireNonNull(end);

        if (start.compareTo(end) >= 0) {
            throw new InconsistentDatePeriodException();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() { return end; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position entry = (Position) o;
        return Objects.equals(title, entry.title) &&
                Objects.equals(description, entry.description) &&
                Objects.equals(start, entry.start) &&
                Objects.equals(end, entry.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, start, end);
    }
}
