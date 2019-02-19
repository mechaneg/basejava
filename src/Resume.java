/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private String uuid;

    String getUuid() {
        return uuid;
    }

    void setUuid(String newUuid) {
        uuid = newUuid;
    }

    @Override
    public String toString() {
        return uuid;
    }
}
