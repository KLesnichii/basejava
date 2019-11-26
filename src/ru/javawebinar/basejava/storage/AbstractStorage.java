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
        return getFromStorage(index, uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = searchResume(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            removeResume(index, uuid);
        }
    }

    /**
     * @return index of the uuid, if it is contained in storage;
     * otherwise return -1;
     */
    protected abstract int searchResume(String uuid);

    protected abstract void setResume(Resume resume, int index);

    protected abstract Resume getFromStorage(int index, String uuid);

    protected abstract void removeResume(int index, String uuid);

    protected abstract void addResume(Resume resume, int index);
}
