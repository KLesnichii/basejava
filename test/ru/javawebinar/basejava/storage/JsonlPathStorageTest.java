package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serialization.JsonStreamSerializer;
import ru.javawebinar.basejava.storage.serialization.XmlStreamSerializer;

public class JsonlPathStorageTest extends AbstractStorageTest {
    public JsonlPathStorageTest() {
        super(new PathStorage(STORAGE_PATH, new JsonStreamSerializer()));
    }
}