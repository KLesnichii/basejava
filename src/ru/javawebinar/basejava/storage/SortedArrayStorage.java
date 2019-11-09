package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {

        //check Overflow
        if (counter == STORAGE_SIZE) {
            System.out.println("Resume " + resume.getUuid() + " save OverflowError");
            return;
        }

        int index = searchResume(resume.getUuid());
        if (index < 0) {
            int SortIndex = Math.abs(index + 1);
            System.arraycopy(storage, SortIndex, storage, SortIndex + 1, counter - SortIndex);
            storage[SortIndex] = resume;
            counter++;
        } else {
            System.out.println("Resume " + resume.getUuid() + " save Error");
        }
    }

    @Override
    protected int searchResume(String uuid) {
        Resume key = new Resume();
        key.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, counter, key);
    }
}
