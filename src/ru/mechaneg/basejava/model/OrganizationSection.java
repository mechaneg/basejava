package ru.mechaneg.basejava.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1;

    private List<Organization> organizations;

    public OrganizationSection() {
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations);
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(organizations, that.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(getClass().getName());
        dos.writeInt(organizations.size());
        for (Organization org : organizations) {
            org.write(dos);
        }
    }

    @Override
    public AbstractSection read(DataInputStream dis) throws IOException {

        Organization orgFactory = new Organization();

        int size = dis.readInt();
        List<Organization> organizations = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            organizations.add(orgFactory.read(dis));
        }

        return new OrganizationSection(organizations);
    }
}
