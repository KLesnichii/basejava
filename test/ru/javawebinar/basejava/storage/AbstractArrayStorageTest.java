package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void save() {
        Resume r = new Resume(UUID_4);
        storage.save(r);
        Assert.assertEquals(r, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        storage.save(new Resume(UUID_1));
    }

    @Test
    public void saveOverflow() {
        storage.clear();
        int i = 0;
        while (i < AbstractArrayStorage.STORAGE_SIZE) {
            storage.save(new Resume());
            i++;
        }
        try {
            storage.save(new Resume());
            Assert.fail();
        } catch (StorageException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void getAll() {
        Resume[] resumeArray = storage.getAll();
        Assert.assertTrue(Arrays.asList(resumeArray).contains(new Resume(UUID_1)));
        Assert.assertTrue(Arrays.asList(resumeArray).contains(new Resume(UUID_2)));
        Assert.assertTrue(Arrays.asList(resumeArray).contains(new Resume(UUID_3)));
        Assert.assertEquals(storage.size(), resumeArray.length);
    }

    @Test
    public void get() {
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertArrayEquals(new Resume[0], storage.getAll());
    }

    @Test
    public void update() {
        Resume r = new Resume(UUID_1);
        storage.update(r);
        Assert.assertSame(r, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        try {
            storage.delete(UUID_3);
        } catch (NotExistStorageException e) {
            Assert.fail();
        }
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}