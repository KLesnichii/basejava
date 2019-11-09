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

    @Override
    public abstract void save(Resume resume);

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
            System.arraycopy(storage, index + 1, storage, index, counter - index - 1);
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

    protected abstract int searchResume(String uuid);

}
