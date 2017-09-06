package com.futur.common.helpers;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SuppressWarnings("unused")
public final class ReflectionHelper {

    private ReflectionHelper() {
        StringHelper.throwNonInitializeable();
    }

    @NotNull
    public static <T extends AccessibleObject> T getAccessible(@NotNull final T t) {
        t.setAccessible(true);
        return t;
    }

    @NotNull
    public static <T> Constructor<T> getConstructor(@NotNull final Class<T> clazz, @NotNull final Class<?>... parameterTypes) throws NoSuchMethodException {
        return getAccessible(clazz.getDeclaredConstructor(parameterTypes));
    }

    @NotNull
    public static Method getMethod(@NotNull final Class<?> clazz, @NotNull final String name, @NotNull final Class<?>... parameterTypes) throws NoSuchMethodException {
        return getAccessible(clazz.getDeclaredMethod(name, parameterTypes));
    }

    @NotNull
    public static Field getField(@NotNull final Class<?> clazz, @NotNull final String fieldName) throws NoSuchFieldException {
        return getAccessible(clazz.getDeclaredField(fieldName));
    }

}
