package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    protected static final Comparator<Resume> RESUME_COMPARATOR_FULL_NAME = (o1, o2) -> {
        int i = o1.getFullName().compareTo(o2.getFullName());
        return (i == 0) ? o1.getUuid().compareTo(o2.getUuid()) : i;
    };

    private static final Logger LOGGER = Logger.getLogger(ArrayStorage.class.getName());

    @Override
    public List<Resume> getAllSorted() {
        LOGGER.info("getAllSorted");
        List<Resume> list = getAllResumeList();
        list.sort(RESUME_COMPARATOR_FULL_NAME);
        return list;
    }

    @Override
    public void save(Resume resume) {
        LOGGER.info("save " + resume);
        String uuid = resume.getUuid();
        addResume(resume, checkNotExist(uuid));
    }

    @Override
    public void update(Resume resume) {
        LOGGER.info("update " + resume);
        String uuid = resume.getUuid();
        updateResume(resume, checkExist(uuid));
    }

    @Override
    public Resume get(String uuid) {
        LOGGER.info("get " + uuid);
        return getFromStorage(checkExist(uuid));
    }

    @Override
    public void delete(String uuid) {
        LOGGER.info("delete " + uuid);
        removeResume(checkExist(uuid));
    }

    /**
     * @return index of the uuid, if it is contained in storage;
     * otherwise return -1;
     */
    protected abstract SK searchResume(String uuid);

    protected abstract List<Resume> getAllResumeList();

    protected abstract void updateResume(Resume resume, SK foundElement);

    protected abstract Resume getFromStorage(SK foundElement);

    protected abstract void removeResume(SK foundElement);

    protected abstract void addResume(Resume resume, SK foundElement);

    protected abstract boolean isExist(SK foundElement);

    private SK checkExist(String uuid) {
        SK foundElement = searchResume(uuid);
        if (!isExist(foundElement)) {
            LOGGER.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return foundElement;
    }

    private SK checkNotExist(String uuid) {
        SK foundElement = searchResume(uuid);
        if (isExist(foundElement)) {
            LOGGER.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return foundElement;
    }

}
