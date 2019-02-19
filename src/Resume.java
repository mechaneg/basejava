/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private String uuid;

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
}
