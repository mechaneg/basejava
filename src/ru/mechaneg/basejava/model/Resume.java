package ru.mechaneg.basejava.model;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private String uuid;

    public Resume() {}

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String newUuid) {
        uuid = newUuid;
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public boolean equals(Object rhs) {
        if (this == rhs) {
            return true;
        }
        if (rhs == null) {
            return false;
        }

        Resume rhsResume = (Resume) rhs;
        return this.uuid.equals(rhsResume.uuid);
    }

    @Override
    public int compareTo(Resume rhs) {
        return this.uuid.compareTo(rhs.uuid);
    }
}
