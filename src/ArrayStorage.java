import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

  private final int maxSize = 10000;
  private Resume[] storage = new Resume[maxSize];
  private int curSize = 0;

  public void clear() {
    Arrays.fill(storage, null);
    curSize = 0;
  }

  public void save(Resume resume) {
    if (resume == null) {
      throw new IllegalArgumentException("resume is null");
    }
    if (curSize == maxSize) {
      throw new StorageOverflowException();
    }

    storage[curSize] = resume;
    curSize++;
  }

  Resume get(String uuid) {
    for (int i = 0; i < curSize; i++) {
      if (storage[i].uuid.equals(uuid)) {
        return storage[i];
      }
    }
    return null;
  }

  void delete(String uuid) {
    for (int i = 0; i < curSize; i++) {
      if (storage[i].uuid.equals(uuid)) {
        storage[i] = storage[curSize - 1];
        storage[curSize - 1] = null;
        curSize--;
        return;
      }
    }
  }

  /**
   * @return array, contains only Resumes in storage (without null)
   */
  Resume[] getAll() {
    return Arrays.copyOf(storage, curSize);
  }

  int size() {
    return curSize;
  }

    /**
     * ArrayStorage specific exceptions
     */
    public class StorageOverflowException extends RuntimeException {}
}
