package com.futur.common.helpers;

import com.futur.common.interfaces.ThrowableUnsafeAction;
import com.futur.common.interfaces.ThrowableUnsafeFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public static void executeSafe(@NotNull ThrowableUnsafeAction unsafe) {
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
     */
    @Nullable
    public static <T> T executeSafe(@NotNull ThrowableUnsafeFunction<T> unsafe) {
        try {
            return unsafe.doAction();
        } catch (Throwable e) {
            LOG.error("Exception during executeSafe", e);
        }

        return null;
    }

}
