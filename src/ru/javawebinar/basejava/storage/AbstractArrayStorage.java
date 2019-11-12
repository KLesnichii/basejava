package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_SIZE = 100_000;
    protected Resume[] storage = new Resume[STORAGE_SIZE];
    protected int counter = 0;

    public void clear() {
        Arrays.fill(storage, 0, counter, null);
        counter = 0;
    }

    public void save(Resume resume) {
        if (counter == STORAGE_SIZE) {//check Overflow
            System.out.println("Resume " + resume.getUuid() + " save OverflowError");
            return;
        }

        int index = searchResume(resume.getUuid());
        if (index >= 0) {
            System.out.println("Resume " + resume.getUuid() + " save Error");
        } else addResume(index, resume);
    }

    public void update(Resume resume) {
        int index = searchResume(resume.getUuid());
        if (index < 0) {
            System.out.println("Resume " + resume.getUuid() + " update Error");
        } else {
            storage[index] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = searchResume(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " get Error");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = searchResume(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " delete Error");
        } else {
            removeResume(index);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null);
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
    protected abstract int searchResume(String uuid);

    protected abstract void addResume(int index, Resume resume);

    protected abstract void removeResume(int index);
}
