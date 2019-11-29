package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_SIZE = 10_000;
    protected Resume[] storage = new Resume[STORAGE_SIZE];
    protected int counter = 0;

    public void clear() {
        Arrays.fill(storage, 0, counter, null);
        counter = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, counter);
    }

    @Override
    public int size() {
        return counter;
    }

    @Override
    protected void addResume(Resume resume, Object index) {
        if (counter == STORAGE_SIZE) {
            throw new StorageException("Storage Overflow", resume.getUuid());
        } else {
            addElement((int) (index), resume);
            counter++;
        }
    }

    @Override
    protected void updateResume(Resume resume, Object index) {
        storage[(int) (index)] = resume;
    }

    @Override
    protected void removeResume(Object index) {
        counter--;
        removeElement((int) (index));
        storage[counter] = null;
    }

    @Override
    protected Resume getFromStorage(Object index) {
        return storage[(int) (index)];
    }

    @Override
    protected boolean isExist(Object index) {
        return (int) index >= 0;
    }

    protected abstract void addElement(int index, Resume resume);

    protected abstract void removeElement(int index);
}
