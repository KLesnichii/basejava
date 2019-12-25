package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Stream;

public class MainFile {

    public static void main(String[] args) {
        String filePath = "basejava/.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File directory = new File("E:\\OldUsers\\shmel\\Desktop\\JavaOps\\basejava\\src");
        printDirectoryFiles(directory, 0);

    }

    private static void printDirectoryFiles(File directory, int recLvCount) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                Stream.generate(() -> "\t").limit(recLvCount).forEach(System.out::print);
                if (f.isFile()) {
                    System.out.println("* " + f.getName());
                } else {
                    System.out.println("■ " + f.getName());
                    printDirectoryFiles(f, recLvCount + 1);
                }
            }
        }
    }


}
