package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String DUMMY = "dummy";
    private static final Resume resume_1;
    private static final Resume resume_2;
    private static final Resume resume_3;
    private static final Resume resume_4;

    static {
        resume_1 = new Resume(UUID_1);
        resume_2 = new Resume(UUID_2);
        resume_3 = new Resume(UUID_3);
        resume_4 = new Resume(DUMMY);

    }


    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUpTest() {
        storage.clear();
        storage.save(resume_1);
        storage.save(resume_2);
        storage.save(resume_3);
    }

    @Test
    public void saveTest() {
        storage.save(resume_4);
        Assert.assertEquals(resume_4, storage.get(DUMMY));
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExistTest() {
        storage.save(resume_1);
    }

    @Test(expected = StorageException.class)
    public void saveOverflowTest() {
        storage.clear();
        int i = 0;
        try {
            while (i < AbstractArrayStorage.STORAGE_SIZE) {
                storage.save(new Resume());
                i++;
            }
        } catch (Exception e) {
            Assert.fail();
        }
        storage.save(new Resume());
    }

    @Test
    public void getAllTest() {
        Resume[] resumeArray = {resume_1, resume_2, resume_3};
        Resume[] getResumeArray = storage.getAll();
        Assert.assertArrayEquals(resumeArray, getResumeArray);
    }

    @Test
    public void getTest() {
        Assert.assertEquals(resume_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistTest() {
        storage.get(DUMMY);
    }

    @Test
    public void clearTest() {
        storage.clear();
        Assert.assertEquals(0, storage.getAll().length);
    }

    @Test
    public void updateTest() {
        storage.update(resume_1);
        Assert.assertSame(resume_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(resume_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteTest() {
        try {
            storage.delete(UUID_3);
        } catch (NotExistStorageException e) {
            Assert.fail();
        }
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete(DUMMY);
    }

    @Test
    public void sizeTest() {
        Assert.assertEquals(3, storage.size());
    }
}