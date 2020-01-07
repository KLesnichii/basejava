package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serialization.DataStreamSerializer;
import ru.javawebinar.basejava.storage.serialization.ObjectStreamSerialization;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_PATH, new DataStreamSerializer()));
    }
}