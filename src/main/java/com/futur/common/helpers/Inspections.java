package com.futur.common.helpers;

import com.google.common.base.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressWarnings("unused")
public final class Inspections {

    private Inspections() {
        StringHelper.throwNonInitializeable();
    }

    @NotNull
    public static Number checkNotNegatively(@Nullable final Number t) throws IllegalArgumentException {
        checkNotNull(t);
        if (t.doubleValue() < 0) {
            throw new IllegalArgumentException();
        }
        return t;
    }

    @NotNull
    public static Number checkNotNegatively(@Nullable final Number t,
                                            @Nullable final Object errorMessage) throws IllegalArgumentException {
        checkNotNull(t);
        if (t.doubleValue() < 0) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
        return t;
    }

    @NotNull
    public static String checkNotNullAndNotEmpty(@Nullable final String s) throws NullPointerException {
        if (Strings.isNullOrEmpty(s)) {
            throw new NullPointerException();
        }
        return s;
    }

    @NotNull
    public static String checkNotNullAndNotEmpty(@Nullable final String s,
                                                 @Nullable final Object errorMessage) throws NullPointerException {
        if (Strings.isNullOrEmpty(s)) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return s;
    }

    public static boolean checkTrue(final boolean assertion) throws IllegalArgumentException {
        if (assertion) {
            return true;
        }
        throw new IllegalArgumentException();
    }

    public static boolean checkTrue(final boolean assertion,
                                    @Nullable final Object errorMessage) throws IllegalArgumentException {
        if (assertion) {
            return true;
        }
        throw new IllegalArgumentException(String.valueOf(errorMessage));
    }

    public static boolean checkFalse(final boolean assertion) throws IllegalArgumentException {
        if (assertion) {
            throw new IllegalArgumentException();
        }
        return false;
    }

    public static boolean checkFalse(final boolean assertion,
                                     @Nullable final Object errorMessage) throws IllegalArgumentException {
        if (assertion) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
        return false;
    }

    public static boolean isAnyNullOrEmpty(@Nullable final String... strings) {
        if (isNullOrEmpty((Object[]) strings)) {
            return false;
        }
        for (String s : strings) {
            if (Strings.isNullOrEmpty(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNullOrEmpty(@Nullable final Object... objects) {
        return objects == null || objects.length == 0;
    }

    public static boolean isNullOrEmpty(@Nullable final Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(@Nullable final Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNullOrEmpty(@Nullable final Double number) {
        return number == null || number == 0;
    }

    public static boolean isNullOrEmpty(@Nullable final double[][] doubles) {
        return doubles == null || doubles.length == 0;
    }

}
