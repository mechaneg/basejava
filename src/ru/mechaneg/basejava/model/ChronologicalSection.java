package ru.mechaneg.basejava.model;

import java.util.List;

public class ChronologicalSection extends ContentSection<List<TimeIntervalRecord>> {
    public ChronologicalSection(List<TimeIntervalRecord> content) {
        super(content);
    }
}
