package com.futur.common.helpers.resources;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

@Slf4j
@SuppressWarnings("unused")
public abstract class ObjectIOHelper {

    @Nullable
    @SuppressWarnings("unchecked")
    private static synchronized <T> T readObject(@NotNull final Class<T> clazz,
                                                 @NotNull final String path) throws IOException, ClassNotFoundException {
        try (@NotNull val oin = new ObjectInputStream(new FileInputStream(path))) {
            @Nullable val o = oin.readObject();
            if (clazz.isInstance(o)) {
                return (T) o;
            }
        }

        return null;
    }

    private static synchronized void writeObject(@NotNull final Object object,
                                                 @NotNull final String path) throws IOException {
        try (@NotNull val oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(object);
        }
    }

    @Nullable
    protected static synchronized <T> T readObjectSafely(@NotNull final Class<T> clazz,
                                                         @NotNull final String path) {
        log.debug("Reading object {} from {}", clazz, path);
        try {
            @Nullable T t = readObject(clazz, path);
            log.debug("Reading done: {}", t);
            return t;
        } catch (Exception e) {
            log.debug("Reading undone", e);
            return null;
        }
    }

    protected static synchronized boolean writeObjectSafely(@NotNull final Object object,
                                                            @NotNull final String path) {
        log.debug("Writing object {} to {}", object.getClass(), path);
        try {
            writeObject(object, path);
            log.debug("Writing done");
            return true;
        } catch (Exception e) {
            log.debug("Writing undone", e);
            return false;
        }
    }

}
