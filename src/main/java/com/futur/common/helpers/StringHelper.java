package com.futur.common.helpers;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class StringHelper {

    public StringHelper() {
        throwNonInitializeable();
    }

    @Contract("-> fail")
    public static void throwNonInitializeable() {
        throw new RuntimeException("No instances of this class for you");
    }

    @NotNull
    public static String numberToString(@NotNull final Number number) {
       return String.valueOf(MathHelper.simpleDouble(number));
    }

}
