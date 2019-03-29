package ru.ifmo.rain.terentev.walk;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class RecursiveWalk {
    private final String inputPath, outputPath;

    public RecursiveWalk(String inputPath, String outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    private void run() throws RecursiveWalkerException {
        String dir;
        if (!Files.isRegularFile(Paths.get(inputPath))) {
            throw new RecursiveWalkerException("Invalid file");
        }
        try {
            Path outPath = Paths.get(outputPath);
            if (outPath.getParent() != null) {
                Files.createDirectories(outPath.getParent());
            }
        } catch (Exception ignored) {
            throw new RecursiveWalkerException("Can't create directories");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath), StandardCharsets.UTF_8))) {
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(outputPath), StandardCharsets.UTF_8)) {
                while ((dir = reader.readLine()) != null) {
                    try {
                        Files.walkFileTree(Paths.get(dir), new FilesVisitor(writer));
                    } catch (InvalidPathException e) {
                        writer.write("00000000 " + dir);
                    }
                }
            } catch (IOException e) {
                throw new RecursiveWalkerException("Can't process output");
            }
        } catch (IOException e) {
            throw new RecursiveWalkerException("Can't process output");
        }
    }

    public static void main(final String[] args) {
        try {
            if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
                throw new RecursiveWalkerException("Invalid args");
            } else {
                RecursiveWalk recursiveWalker = new RecursiveWalk(args[0], args[1]);
                recursiveWalker.run();
            }
        } catch (RecursiveWalkerException e) {
            System.err.println(e.getMessage());
        }
    }

}
