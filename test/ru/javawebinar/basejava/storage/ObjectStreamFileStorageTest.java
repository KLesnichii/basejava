package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serialization.ObjectStreamSerialization;

import java.io.File;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new FileStorage(new File(STORAGE_PATH), new ObjectStreamSerialization()));
    }
}