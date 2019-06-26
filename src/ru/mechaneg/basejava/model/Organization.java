package ru.mechaneg.basejava.model;

import ru.mechaneg.basejava.exception.StorageException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1;

    private String company;
    private String companyUrl;
    private List<Position> positions;

    public Organization() {
    }

    public Organization(String company, String companyUrl, List<Position> positions) {
        this.company = company;
        this.companyUrl = companyUrl;
        this.positions = positions;

        Objects.requireNonNull(company);
        Objects.requireNonNull(positions);
        if (positions.isEmpty()) {
            throw new StorageException("Positions shouldn't be empty");
        }
    }

    public String getCompany() {
        return company;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(company, that.company) &&
                Objects.equals(companyUrl, that.companyUrl) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, companyUrl, positions);
    }
}
