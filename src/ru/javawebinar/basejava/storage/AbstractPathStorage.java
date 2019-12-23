package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected AbstractPathStorage(String dir) {
        Path directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.directory = directory;
    }

    @Override
    protected Path searchResume(String uuid) {
        return   directory.resolve(uuid);
    }

    @Override
    protected List<Resume> getAllResumeList() {
        try {
            return new ArrayList<Resume>((Collection<? extends Resume>) Files.list(directory).collect(Collectors.toList()));
        } catch (IOException e) {
            throw new StorageException("NullPointerException", null);
        }
    }

    @Override
    protected void updateResume(Resume resume, Path path) {
        try {
            doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume getFromStorage(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void removeResume(Path Path) {
        if (!Path.delete()) throw new StorageException("Delete error", Path.getName());
    }

    @Override
    protected void addResume(Resume resume, Path Path) {
        try {
            Path.createNewPath();
            updateResume(resume, Path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + Path.getAbsolutePath(), Path.getName(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path,NOFOLLOW_LINKS);
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::removeResume);
        } catch (IOException e) {
            throw new StorageException("NullPointerException", null);
        }
    }

    @Override
    public int size() {
        try {
            return  (int)Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("NullPointerException", null);
        }
    }


    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

}
