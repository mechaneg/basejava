package ru.mechaneg.basejava.model;

import ru.mechaneg.basejava.util.DataSerializable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable, DataSerializable {
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

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(company);
        dos.writeUTF(companyUrl);
        dos.writeInt(positions.size());
        for (Position pos : positions) {
            pos.write(dos);
        }
    }

    @Override
    public Organization read(DataInputStream dis) throws IOException {
        String company = dis.readUTF();
        String companyUrl = dis.readUTF();

        Position positionFactory = new Position();

        List<Position> positions = new ArrayList<>();
        int positionsSize = dis.readInt();
        for (int i = 0; i < positionsSize; i++) {
            positions.add(positionFactory.read(dis));
        }

        return new Organization(company, companyUrl, positions);
    }
}
