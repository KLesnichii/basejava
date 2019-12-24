package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private StorageSerialization serialization;
    private File directory;

    protected FileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    public FileStorage(File directory, StorageSerialization serialization) {
        this(directory);
        this.serialization = serialization;
    }

    public void setSerialization(StorageSerialization serialization) {
        this.serialization = serialization;
    }

    @Override
    protected File searchResume(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected List<Resume> getAllResumeList() {
        List<Resume> storage = new ArrayList<>();
        for (File f : checkNullException()) {
            storage.add(getFromStorage(f));
        }
        return storage;
    }

    @Override
    protected void updateResume(Resume resume, File file) {
        try {
            serialization.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getFromStorage(File file) {
        try {
            return serialization.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void removeResume(File file) {
        if (!file.delete()) throw new StorageException("Delete error", file.getName());
    }

    @Override
    protected void addResume(Resume resume, File file) {
        try {
            file.createNewFile();
            updateResume(resume, file);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void clear() {
        for (File f : checkNullException()) {
            removeResume(f);
        }
    }

    @Override
    public int size() {
        return checkNullException().length;
    }

    private File[] checkNullException() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("NullPointerException", directory.getName());
        } else {
            return files;
        }
    }
}
