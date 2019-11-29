package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        Object foundElement = searchResume(uuid);

        if (isExist(foundElement)) {
            throw new ExistStorageException(uuid);
        } else addResume(resume, foundElement);
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        updateResume(resume,  checkNotExist(uuid));
    }

    @Override
    public Resume get(String uuid) {
        return getFromStorage(checkNotExist(uuid));
    }

    @Override
    public void delete(String uuid) {
        removeResume(checkNotExist(uuid));
    }

    /**
     * @return index of the uuid, if it is contained in storage;
     * otherwise return -1;
     */
    protected abstract Object searchResume(String uuid);

    protected abstract void updateResume(Resume resume, Object foundElement);

    protected abstract Resume getFromStorage(Object foundElement);

    protected abstract void removeResume(Object foundElement);

    protected abstract void addResume(Resume resume, Object foundElement);

    protected abstract boolean isExist(Object foundElement);

    private Object checkNotExist(String uuid) {
        Object foundElement = searchResume(uuid);
        if (!isExist(foundElement)) {
            throw new NotExistStorageException(uuid);
        }
        return foundElement;
    }

}
