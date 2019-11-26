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
    protected void addResume(Resume resume, int index) {
        if (counter == STORAGE_SIZE) {
            throw new StorageException("Storage Overflow", resume.getUuid());
        } else {
            addElement(index, resume);
            counter++;
        }
    }

    @Override
    protected void setResume(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected void removeResume(int index) {
        counter--;
        removeElement(index);
        storage[counter] = null;
    }

    @Override
    protected Resume getFromStorage(int index) {
        return storage[index];
    }

    protected abstract void addElement(int index, Resume resume);

    protected abstract void removeElement(int index);
}
