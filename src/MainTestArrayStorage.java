/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());

        String uuidForUpdate = "uuidForUpdate";
        Resume r4 = new Resume();
        r4.setUuid(uuidForUpdate);
        ARRAY_STORAGE.save(r4);

        Resume r5 = new Resume();
        r5.setUuid(uuidForUpdate);
        ARRAY_STORAGE.update(r5);

        System.out.println();
        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println("Get uuidForUpdate: " + ARRAY_STORAGE.get(uuidForUpdate));
        if (ARRAY_STORAGE.get(uuidForUpdate) == r5) {
            System.out.println("r4 is replaced with r5");
        }
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
