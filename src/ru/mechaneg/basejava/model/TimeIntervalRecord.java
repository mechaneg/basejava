package ru.mechaneg.basejava.model;

import ru.mechaneg.basejava.exception.InconsistentTimeIntervalException;
import java.time.LocalDate;

public class TimeIntervalRecord {
    private String name;
    private String description;
    private LocalDate start;
    private LocalDate end;

    public TimeIntervalRecord(String name, String description, LocalDate start, LocalDate end) {
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;

        if (start.compareTo(end) >= 0) {
            throw new InconsistentTimeIntervalException();
        }
    }

    public String getName() {
        return name;
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
    public String toString() {
        return "TimeIntervalRecord{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
