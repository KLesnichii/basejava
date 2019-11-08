package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_SIZE = 100_000;
    protected Resume[] storage = new Resume[STORAGE_SIZE];
    protected int counter = 0;

    public int size() {
        return counter;
    }

    public Resume get(String uuid) {
        int index = searchResume(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " get Error");
            return null;
        }
        return storage[index];
    }

    protected abstract int searchResume(String uuid);

}
