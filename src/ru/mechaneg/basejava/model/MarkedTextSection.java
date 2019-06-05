package ru.mechaneg.basejava.model;

import java.util.List;
import java.util.Objects;

public class MarkedTextSection extends AbstractSection {
    private static final long serialVersionUID = 1;

    private List<String> items;

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
}
