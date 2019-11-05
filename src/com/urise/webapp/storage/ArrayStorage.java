package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_SIZE = 10000;
    private Resume[] storage = new Resume[STORAGE_SIZE];
    private int counter = 0;

    public void clear() {
        Arrays.fill(storage, 0, counter, null);
        counter = 0;
    }

    public void save(Resume r) {
        int index = searchResume(r.getUuid());
        //check Overflow
        if (counter == STORAGE_SIZE) {
            System.out.println("Resume save OverflowError");
            return;
        }
        if (index == -1) {
            storage[counter] = r;
            counter++;
        } else {
            System.out.println("Resume save Error");
        }
    }

    public void update(Resume r) {
        int index = searchResume(r.getUuid());
        if (index == -1) {
            System.out.println("Resume update Error");
        } else {
            storage[index] = r;
        }
    }

    public Resume get(String uuid) {
        int index = searchResume(uuid);
        if (index == -1) {
            System.out.println("Resume get Error");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = searchResume(uuid);
        if (index == -1) {
            System.out.println("Resume delete Error");
        } else {
            //check ArrayIndexOutOfBounds
            if (index != STORAGE_SIZE - 1) {
                System.arraycopy(storage, index + 1, storage, index, counter - index - 1);
            }
            counter--;
            storage[counter] = null;
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

    /**
     * @return index of the uuid, if it is contained in storage;
     * otherwise return -1;
     */
    private int searchResume(String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
