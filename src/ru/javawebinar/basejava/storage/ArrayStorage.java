package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected void addResume(int index, Resume resume) {
        storage[counter] = resume;
        counter++;
    }

    @Override
    protected int searchResume(String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void removeResume(int index) {
        counter--;
        storage[index] = storage[counter];
        storage[counter] = null;
    }
}
