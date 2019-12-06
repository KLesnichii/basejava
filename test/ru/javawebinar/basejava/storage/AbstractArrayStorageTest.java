package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflowTest() {
        storage.clear();
        int i = 0;
        try {
            while (i < AbstractArrayStorage.STORAGE_SIZE) {
                storage.save(new Resume(""));
                i++;
            }
        } catch (StorageException e) {
            Assert.fail("StorageException when save Resume, not Overflow");
        }
        storage.save(new Resume(""));
    }
}