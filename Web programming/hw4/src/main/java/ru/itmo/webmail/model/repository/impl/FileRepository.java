package ru.itmo.webmail.model.repository.impl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepository<T> {

    private static final File tmpDir = new File(System.getProperty("java.io.tmpdir"));
    protected List<T> data;
    private String name;

    public FileRepository(String name) {
        this.name = name;
        try {
            //noinspection unchecked
            data = (List<T>) new ObjectInputStream(
                    new FileInputStream(new File(tmpDir, name))).readObject();
        } catch (Exception e) {
            data = new ArrayList<>();
        }
    }

    public void save(T news) {
        data.add(news);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(new File(tmpDir, name)));
            objectOutputStream.writeObject(data);
            objectOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException("Can't save data.", e);
        }
    }

    public List<T> findAll() {
        return new ArrayList<>(data);
    }

    public int findCount() {
        return data.size();
    }
}