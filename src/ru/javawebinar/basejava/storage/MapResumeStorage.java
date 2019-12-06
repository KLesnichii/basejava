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
        return storage.get(uuid);
    }

    @Override
    protected void updateResume(Resume resume, Object foundResume) {
        storage.put(((Resume) foundResume).getUuid(), resume);
    }

    @Override
    protected Resume getFromStorage(Object foundResume) {
        return (Resume) foundResume;
    }

    @Override
    protected void removeResume(Object foundResume) {
        storage.remove(((Resume) foundResume).getUuid());
    }

    @Override
    protected void addResume(Resume resume, Object foundResume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Object foundResume) {
        return foundResume != null;
    }

}
