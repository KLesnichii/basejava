import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int counter = 0;

    void clear() {
        Arrays.fill(storage, null);
        counter = 0;
    }

    void save(Resume r) {
        storage[counter] = r;
        counter++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].uuid.equals(uuid)) return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                if (i < counter - 1) System.arraycopy(storage, i + 1, storage, i, counter - i - 1);
                counter--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, counter);
    }

    int size() {
        return counter;
    }
}
