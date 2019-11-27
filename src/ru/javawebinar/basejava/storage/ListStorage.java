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
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getFromStorage(int index) {
        return storage.get(index);
    }

    @Override
    protected void setResume(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected void removeResume(int index) {
        storage.remove(index);
    }

    @Override
    protected void addResume(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected int searchResume(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}




