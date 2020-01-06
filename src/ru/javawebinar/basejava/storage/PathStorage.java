package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serialization.StreamSerialization;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private StreamSerialization serialization;
    private Path directory;

    public PathStorage(String dir, StreamSerialization serialization) {
        Path directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.directory = directory;
        this.serialization = Objects.requireNonNull(serialization, "serialization must not be null");
    }

    @Override
    protected Path searchResume(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected List<Resume> getAllResumeList() {
        return pathToStream().map(this::getFromStorage).collect(Collectors.toList());
    }

    @Override
    protected void updateResume(Resume resume, Path path) {
        try {
            serialization.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume getFromStorage(Path path) {
        try {
            return serialization.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void removeResume(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StorageException("Delete error", path.getFileName().toString());
        }
    }

    @Override
    protected void addResume(Resume resume, Path path) {
        try {
            Files.createFile(path);
            updateResume(resume, path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + path.toAbsolutePath(), path.getFileName().toString(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    public void clear() {
        pathToStream().forEach(this::removeResume);
    }

    @Override
    public int size() {
        return (int) pathToStream().count();
    }

    private Stream<Path> pathToStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("NullPointerException", null);
        }
    }
}
