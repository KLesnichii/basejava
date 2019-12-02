package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getAllResumeList() {
        return new ArrayList<>(storage.values());
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
    protected void updateResume(Resume resume, Object uuid) {
        storage.put((String) uuid, resume);
    }

    @Override
    protected Resume getFromStorage(Object uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void removeResume(Object uuid) {
        storage.remove(uuid);
    }

    @Override
    protected void addResume(Resume resume, Object uuid) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Object uuid) {
        return uuid != "";
    }
}
