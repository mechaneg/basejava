import ru.mechaneg.basejava.model.Resume;
import ru.mechaneg.basejava.storage.IStorage;
import ru.mechaneg.basejava.storage.SortedArrayStorage;

/**
 * Run this test with -ea (enableassertions) flag
 * Otherwise it is always successful
 */
public class SortedArrayStorageTest {
    public static void main(String... args) {
        try {
            IStorage storage = new SortedArrayStorage();

            assert storage.size() == 0;
            assert storage.get("any uuid") == null;

            storage.save(new Resume("1"));
            assert storage.size() == 1;
            assert storage.get("1").getUuid().equals("1");

            storage.save(new Resume("3"));
            storage.save(new Resume("2"));

            {
                Resume[] all = storage.getAll();
                assert all.length == 3;
                assert all[0].getUuid().equals("1");
                assert all[1].getUuid().equals("2");
                assert all[2].getUuid().equals("3");
            }

            storage.delete("1");

            {
                Resume[] all = storage.getAll();
                assert all.length == 2;
                assert all[0].getUuid().equals("2");
                assert all[1].getUuid().equals("3");
            }

            Resume new2 = new Resume("2");
            storage.update(new2);
            assert storage.get("2") == new2;

        } catch (AssertionError e) {
            System.out.println("Tests failed");
            return;
        }
        System.out.println("Tests passed");
    }
}
