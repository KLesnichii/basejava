package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected void updateResume(Resume resume, Resume foundResume) {
        storage.put(foundResume.getUuid(), resume);
    }

    @Override
    protected Resume getFromStorage(Resume foundResume) {
        return foundResume;
    }

    @Override
    protected void removeResume(Resume foundResume) {
        storage.remove(foundResume.getUuid());
    }

    @Override
    protected void addResume(Resume resume, Resume foundResume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Resume foundResume) {
        return Objects.nonNull(foundResume);
    }

}
