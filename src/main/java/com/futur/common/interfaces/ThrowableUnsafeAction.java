package com.futur.common.interfaces;

@FunctionalInterface
public interface ThrowableUnsafeAction {

    void doAction() throws Throwable;

}