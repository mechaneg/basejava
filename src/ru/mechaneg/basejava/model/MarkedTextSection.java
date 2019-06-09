package ru.mechaneg.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class MarkedTextSection extends AbstractSection {
    private static final long serialVersionUID = 1;

    private List<String> items;

    public MarkedTextSection() {
    }

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
