package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListStorage extends AbstractStorage<Integer> {
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
    protected Resume getFromStorage(Integer foundElement) {
        return storage.get((foundElement));
    }

    @Override
    protected void updateResume(Resume resume, Integer foundElement) {
        storage.set(foundElement, resume);
    }

    @Override
    protected void removeResume(Integer foundElement) {
        storage.remove(foundElement.intValue());
    }

    @Override
    protected void addResume(Resume resume, Integer foundElement) {
        storage.add(resume);
    }

    @Override
    protected Integer searchResume(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Integer foundElement) {
        return Objects.nonNull(foundElement);
    }
}




