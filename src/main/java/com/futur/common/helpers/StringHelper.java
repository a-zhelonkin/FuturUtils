package com.futur.common.helpers;

import org.jetbrains.annotations.Contract;

public final class StringHelper {

    public StringHelper() {
        throwNonInitializeable();
    }

    @Contract("-> fail")
    public static void throwNonInitializeable() {
        throw new RuntimeException("No instances of this class for you");
    }

}
