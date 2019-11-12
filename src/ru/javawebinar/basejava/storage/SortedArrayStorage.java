package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected void addResume(int index, Resume resume) {
        int SortIndex = Math.abs(index + 1);
        System.arraycopy(storage, SortIndex, storage, SortIndex + 1, counter - SortIndex);
        storage[SortIndex] = resume;
        counter++;
    }

    @Override
    protected int searchResume(String uuid) {
        Resume key = new Resume();
        key.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, counter, key);
    }

    @Override
    protected void removeResume(int index) {
        counter--;
        System.arraycopy(storage, index + 1, storage, index, counter - index);
        storage[counter] = null;
    }
}
