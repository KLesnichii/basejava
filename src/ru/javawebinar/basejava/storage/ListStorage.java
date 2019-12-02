package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getAllResumeList() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getFromStorage(Object foundElement) {
        return storage.get((int) (foundElement));
    }

    @Override
    protected void updateResume(Resume resume, Object foundElement) {
        storage.set((int) (foundElement), resume);
    }

    @Override
    protected void removeResume(Object foundElement) {
        storage.remove((int) foundElement);
    }

    @Override
    protected void addResume(Resume resume, Object foundElement) {
        storage.add(resume);
    }

    @Override
    protected Integer searchResume(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object foundElement) {
        return (int) foundElement >= 0;
    }
}




