package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        int index = searchResume(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else addResume(resume, index);
    }

    @Override
    public void update(Resume resume) {
        int index = searchResume(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            setResume(resume, index);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = searchResume(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getFromStorage(index);
    }

    @Override
    public void delete(String uuid) {
        int index = searchResume(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            removeResume(index);
        }
    }

    @Override
    public abstract void clear();

    /**
     * @return array, contains only Resumes in storage (without null);
     */
    @Override
    public abstract Resume[] getAll();

    @Override
    public abstract int size();

    /**
     * @return index of the uuid, if it is contained in storage;
     * otherwise return -1;
     */
    protected abstract int searchResume(String uuid);

    protected abstract void setResume(Resume resume, int index);

    protected abstract Resume getFromStorage(int index);

    protected abstract void removeResume(int index);

    protected abstract void addResume(Resume resume, int index);
}
