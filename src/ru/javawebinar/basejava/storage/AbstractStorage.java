package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected static final Comparator<Resume> RESUME_COMPARATOR_FULL_NAME = (o1, o2) -> {
        int i = o1.getFullName().compareTo(o2.getFullName());
        return (i == 0) ? o1.getUuid().compareTo(o2.getUuid()) : i;
    };

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getAllResumeList();
        list.sort(RESUME_COMPARATOR_FULL_NAME);
        return list;
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        addResume(resume, checkNotExist(uuid));
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        updateResume(resume, checkExist(uuid));
    }

    @Override
    public Resume get(String uuid) {
        return getFromStorage(checkExist(uuid));
    }

    @Override
    public void delete(String uuid) {
        removeResume(checkExist(uuid));
    }

    /**
     * @return index of the uuid, if it is contained in storage;
     * otherwise return -1;
     */
    protected abstract Object searchResume(String uuid);

    protected abstract List<Resume> getAllResumeList();

    protected abstract void updateResume(Resume resume, Object foundElement);

    protected abstract Resume getFromStorage(Object foundElement);

    protected abstract void removeResume(Object foundElement);

    protected abstract void addResume(Resume resume, Object foundElement);

    protected abstract boolean isExist(Object foundElement);

    private Object checkExist(String uuid) {
        Object foundElement = searchResume(uuid);
        if (!isExist(foundElement)) {
            throw new NotExistStorageException(uuid);
        }
        return foundElement;
    }

    private Object checkNotExist(String uuid) {
        Object foundElement = searchResume(uuid);
        if (isExist(foundElement)) {
            throw new ExistStorageException(uuid);
        }
        return foundElement;
    }

}
