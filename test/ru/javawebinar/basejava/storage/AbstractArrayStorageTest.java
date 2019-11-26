package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    private Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
        this.storage = storage;
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
}