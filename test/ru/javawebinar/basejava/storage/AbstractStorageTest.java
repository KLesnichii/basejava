package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String DUMMY = "dummy";
    private static final String NAME1 = "B";
    private static final String NAME2 = "A";
    private static final String NAME3 = "A";
    private static final String NAME4 = "C";
    private static final Resume resume_1;
    private static final Resume resume_2;
    private static final Resume resume_3;
    private static final Resume resume_4;

    static {
        resume_1 = new Resume(UUID_1, NAME1);
        resume_2 = new Resume(UUID_2, NAME2);
        resume_3 = new Resume(UUID_3, NAME3);
        resume_4 = new Resume(DUMMY, NAME4);

    }

    protected AbstractStorageTest(Storage storage) {
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
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExistTest() {
        storage.save(resume_1);
    }

    @Test
    public void getAllSortedTest() {
        Resume[] expectedResumes = {resume_2, resume_3, resume_1};
        Resume[] actualResumes = storage.getAllSorted().toArray(new Resume[0]);
        Assert.assertArrayEquals(expectedResumes, actualResumes);
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
        Assert.assertEquals(0, storage.getAllSorted().size());
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