package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
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
    protected Resume searchResume(String uuid) {
        Resume resume = new Resume(uuid);
        return storage.values().contains(resume) ? resume : null;
    }

    @Override
    protected void updateResume(Resume resume, Object foundElement) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getFromStorage(Object foundElement) {
        for (Resume r : storage.values()) {
            if (r.equals(foundElement)) return r;
        }
        return null;
    }

    @Override
    protected void removeResume(Object foundElement) {
        storage.values().remove(foundElement);
    }

    @Override
    protected void addResume(Resume resume, Object foundElement) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Object foundElement) {
        return foundElement != null;
    }

}
