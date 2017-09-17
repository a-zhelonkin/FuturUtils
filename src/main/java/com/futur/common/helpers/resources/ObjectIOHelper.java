package com.futur.common.helpers.resources;

import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

@SuppressWarnings("unused")
public abstract class ObjectIOHelper {

    @NotNull
    private static final Logger LOG = LoggerFactory.getLogger(ObjectIOHelper.class);

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
    protected static synchronized <T> T read_Object_Safely(@NotNull final Class<T> clazz,
                                                           @NotNull final String path) {
        LOG.debug("Reading object {} from {}", clazz, path);
        try {
            @Nullable T t = readObject(clazz, path);
            LOG.debug("Reading done: {}", t);
            return t;
        } catch (Exception e) {
            LOG.debug("Reading undone", e);
            return null;
        }
    }

    protected static synchronized boolean write_Object_Safely(@NotNull final Object object,
                                                              @NotNull final String path) {
        LOG.debug("Writing object {} to {}", object.getClass(), path);
        try {
            writeObject(object, path);
            LOG.debug("Writing done");
            return true;
        } catch (Exception e) {
            LOG.debug("Writing undone", e);
            return false;
        }
    }

}
