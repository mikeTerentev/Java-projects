package ru.ifmo.rain.terentev.walk;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.*;

public class FilesVisitor extends SimpleFileVisitor<Path> {
    private Writer writer;

    FilesVisitor(Writer writer) {
        this.writer = writer;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
        int chAmount;
        String dir = file.toString();
        FNVHash hasher = new FNVHash();
        try (InputStream fileReader = new FileInputStream(dir)) {
            byte[] chBuf = new byte[8196];
            while ((chAmount = fileReader.read(chBuf, 0, 8196)) >= 0) {
                hasher.update(chBuf, chAmount);
            }
            writer.write(String.format("%08x %s", hasher.getHash(), dir));
        } catch (IOException e) {
            writer.write("00000000 " + dir );
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        writer.write("00000000 " + file.toString());
        return CONTINUE;
    }
}
