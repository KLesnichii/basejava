package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> storage = new HashMap<>();

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
    protected int searchResume(String uuid) {
        if (storage.containsKey(uuid)) return 1;
        return -1;
    }

    @Override
    protected void setResume(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getFromStorage(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void removeResume(int index, String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected void addResume(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }
}
