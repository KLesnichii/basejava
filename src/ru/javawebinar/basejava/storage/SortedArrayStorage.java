package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected void addElement(int index, Resume resume) {
        int sortIndex = -index - 1;
        System.arraycopy(storage, sortIndex, storage, sortIndex + 1, counter - sortIndex);
        storage[sortIndex] = resume;
    }

    @Override
    protected Integer searchResume(String uuid) {
        Resume key = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, counter, key);
    }

    @Override
    protected void removeElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, counter - index);
    }
}
