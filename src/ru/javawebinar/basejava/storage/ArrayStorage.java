package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume resume) {

        //check Overflow
        if (counter == STORAGE_SIZE) {
            System.out.println("Resume " + resume.getUuid() + " save OverflowError");
            return;
        }

        int index = searchResume(resume.getUuid());
        if (index < 0) {
            storage[counter] = resume;
            counter++;
        } else {
            System.out.println("Resume " + resume.getUuid() + " save Error");
        }
    }

    /**
     * @return index of the uuid, if it is contained in storage;
     * otherwise return -1;
     */
    protected int searchResume(String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
