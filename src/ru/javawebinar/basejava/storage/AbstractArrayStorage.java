package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_SIZE = 10_000;
    protected Resume[] storage = new Resume[STORAGE_SIZE];
    protected int counter = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, counter, null);
        counter = 0;
    }

    @Override
    protected List<Resume> getAllResumeList() {
        return new ArrayList<>(Arrays.asList(Arrays.copyOf(storage, counter)));
    }

    @Override
    public int size() {
        return counter;
    }

    @Override
    protected void addResume(Resume resume, Integer index) {
        if (counter == STORAGE_SIZE) {
            throw new StorageException("Storage Overflow", resume.getUuid());
        } else {
            addElement(index, resume);
            counter++;
        }
    }

    @Override
    protected void updateResume(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    protected void removeResume(Integer index) {
        counter--;
        removeElement(index);
        storage[counter] = null;
    }

    @Override
    protected Resume getFromStorage(Integer index) {
        return storage[index];
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    protected abstract void addElement(int index, Resume resume);

    protected abstract void removeElement(int index);
}
