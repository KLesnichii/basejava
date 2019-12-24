package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    private static int recLvCount = 0;

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
        printDirectoryFiles(directory);

    }

    private static void printDirectoryFiles(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    shift(recLvCount);
                    System.out.println("* " + f.getName());
                } else {
                    shift(recLvCount);
                    System.out.println("■ " + f.getName());
                    recLvCount++;
                    printDirectoryFiles(f);
                }
            }
        }
        recLvCount--;
    }

    private static void shift(int number) {
        for (int i = 0; i < number; i++) {
            System.out.print("\t");
        }
    }
}
