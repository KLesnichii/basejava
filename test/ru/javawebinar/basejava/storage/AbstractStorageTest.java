package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.util.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import static ru.javawebinar.basejava.model.ResumeTestData.*;

public abstract class AbstractStorageTest {
    protected static final String STORAGE_PATH = Config.getInstance().getStorageDir().getAbsolutePath();
    protected Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUpTest() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void saveTest() {
        storage.save(resumeU);
        Assert.assertEquals(resumeU, storage.get(UUID_U));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExistTest() {
        storage.save(resume1);
    }

    @Test
    public void getAllSortedTest() {
        storage.save(resume4);
        Resume[] expectedResumes = {resume2, resume3, resume1, resume4};
        Resume[] actualResumes = storage.getAllSorted().toArray(new Resume[0]);
        Assert.assertArrayEquals(expectedResumes, actualResumes);
    }

    @Test
    public void getTest() {
        Assert.assertEquals(resume1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistTest() {
        storage.get(DUMMY);
    }

    @Test
    public void clearTest() {
        storage.clear();
        Assert.assertEquals(0, storage.getAllSorted().size());
    }

    @Test
    public void updateTest() {
        storage.save(resumeU);
        storage.update(resumeU1);
        Assert.assertEquals(resumeU1, storage.get(UUID_U));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(resume4);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteTest() {
        try {
            storage.delete(UUID_3);
        } catch (NotExistStorageException e) {
            Assert.fail();
        }
        Assert.assertEquals(2, storage.size());
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