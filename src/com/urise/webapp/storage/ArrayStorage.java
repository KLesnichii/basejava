package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_SIZE = 10_000;
    private Resume[] storage = new Resume[STORAGE_SIZE];
    private int counter = 0;

    public void clear() {
        Arrays.fill(storage, 0, counter, null);
        counter = 0;
    }

    public void save(Resume resume) {
        int index = searchResume(resume.getUuid());
        //check Overflow
        if (counter == STORAGE_SIZE) {
            System.out.println("Resume " + resume.getUuid() + " save OverflowError");
            return;
        }
        if (index == -1) {
            storage[counter] = resume;
            counter++;
        } else {
            System.out.println("Resume " + resume.getUuid() + " save Error");
        }
    }

    public void update(Resume resume) {
        int index = searchResume(resume.getUuid());
        if (index == -1) {
            System.out.println("Resume " + resume.getUuid() + " update Error");
        } else {
            storage[index] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = searchResume(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " get Error");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = searchResume(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " delete Error");
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
