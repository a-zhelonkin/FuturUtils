package com.futur.common.helpers;

import org.jetbrains.annotations.NotNull;

public final class MathHelper {

    @NotNull
    public static Number simpleDouble(@NotNull final Number number) {
        if (number.doubleValue() == number.intValue()) {
            return number.intValue();
        } else {
            return number;
        }
    }

}
