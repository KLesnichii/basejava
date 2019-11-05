package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int counter = 0;

    public void clear() {
        Arrays.fill(storage, 0, counter, null);
        counter = 0;
    }

    public void save(Resume r) {
        storage[counter] = r;
        counter++;
    }

    public void update(Resume r) {
        storage[counter] = r;
        counter++;
    }

    public Resume get(String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, counter - i - 1);
                counter--;
                storage[counter] = null;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, counter);
    }

    public int size() {
        return counter;
    }
}
