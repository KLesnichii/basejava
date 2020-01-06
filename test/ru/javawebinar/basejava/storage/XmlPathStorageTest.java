package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serialization.ObjectStreamSerialization;
import ru.javawebinar.basejava.storage.serialization.XmlStreamSerializer;

import java.io.File;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_PATH, new XmlStreamSerializer()));
    }
}