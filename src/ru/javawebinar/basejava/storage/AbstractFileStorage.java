package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected File searchResume(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected List<Resume> getAllResumeList() {
        List<Resume> storage = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                storage.add(getFromStorage(f));
            }
        } else {
            throw new StorageException("NullPointerException", directory.getName());
        }
        return storage;
    }

    @Override
    protected void updateResume(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getFromStorage(File file) {
        try {
            return doRead(file);
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
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                removeResume(f);
            }
        } else {
            throw new StorageException("NullPointerException", directory.getName());
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        if (files != null) {
            return files.length;
        } else {
            throw new StorageException("NullPointerException", directory.getName());
        }
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;
}
