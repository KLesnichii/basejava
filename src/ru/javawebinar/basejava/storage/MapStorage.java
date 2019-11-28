package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected String searchResume(String uuid) {
        return storage.containsKey(uuid) ? uuid : "";
    }

    @Override
    protected void updateResume(Resume resume, Object foundElement) {
        storage.put((String) foundElement, resume);
    }

    @Override
    protected Resume getFromStorage(Object foundElement) {
        return storage.get(foundElement);
    }

    @Override
    protected void removeResume(Object foundElement) {
        storage.remove(foundElement);
    }

    @Override
    protected void addResume(Resume resume, Object foundElement) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Object foundElement) {
        return foundElement != "";
    }
}
