package com.futur.common.helpers;

import com.futur.common.interfaces.ThrowableUnsafeAction;
import com.futur.common.interfaces.ThrowableUnsafeFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("unused")
public final class DevelopmentHelper {

    @NotNull
    private static final Logger LOG = LoggerFactory.getLogger(DevelopmentHelper.class);

    public DevelopmentHelper() {
        StringHelper.throwNonInitializeable();
    }

    /**
     * Выполняет указанное действие,
     * обрабатывая возникаюищие исключения
     * выводом в лог
     *
     * @param unsafe Небезопасная операция.
     */
    public static void executeSafe(@NotNull final ThrowableUnsafeAction unsafe) {
        try {
            unsafe.doAction();
        } catch (Throwable e) {
            LOG.error("Exception during executeSafe", e);
        }
    }

    /**
     * Выполняет указанное действие,
     * обрабатывая возникаюищие исключения
     * выводом в лог
     *
     * @param unsafe Небезопасная Функция.
     * @param <T>    type
     * @return type
     */
    @Nullable
    public static <T> T executeSafe(@NotNull final ThrowableUnsafeFunction<T> unsafe) {
        try {
            return unsafe.doAction();
        } catch (Throwable e) {
            LOG.error("Exception during executeSafe", e);
        }

        return null;
    }

    public static <T> void ifNotNull(@Nullable final T t, @NotNull final Consumer<T> consumer) {
        if (t != null) {
            consumer.accept(t);
        }
    }

    @Nullable
    public static <T, R> R ifNotNull(@Nullable final T t, @NotNull final Function<T, R> function) {
        return t == null ? null : function.apply(t);
    }

}
