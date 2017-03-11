package com.futur.common.interfaces;

import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ThrowableUnsafeFunction<T> {

    @Nullable T doAction() throws Throwable;

}