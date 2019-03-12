package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> BIN_SEARCH_COMPARATOR = new Comparator<Resume>() {
        @Override
        public int compare(Resume lhs, Resume rhs) {
            return lhs.getUuid().compareTo(rhs.getUuid());
        }
    };

    private static final String STUB_FULLNAME = "stub";

    /**
     * @return Imitates Arrays.binarySearch behaviour
     * <p>
     */
    @Override
    protected Object findSearchKey(String uuid) {
        return Arrays.binarySearch(storage, 0, curSize, new Resume(uuid, STUB_FULLNAME), BIN_SEARCH_COMPARATOR);
    }

    @Override
    protected int prepareToInsert(int position) {
        position = -(position + 1);
        System.arraycopy(storage, position, storage, position + 1, curSize - position);
        return position;
    }

    @Override
    protected void doDeleteAtPosition(int position) {
        System.arraycopy(storage, position + 1, storage, position, curSize - position - 1);
    }
}
