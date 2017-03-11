package com.futur.ui;

import com.futur.common.helpers.StringHelper;
import javafx.scene.control.TextInputControl;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.futur.common.helpers.DevelopmentHelper.executeSafe;

@SuppressWarnings("unused")
public final class Connector {

    private Connector() {
        StringHelper.throwNonInitializeable();
    }

    public static void connectString(@NotNull final TextInputControl textInputControl,
                                     @NotNull final Consumer<String> consumer) {
        connect(textInputControl, consumer, Function.identity());
    }

    public static void connectInteger(@NotNull final TextInputControl textInputControl,
                                      @NotNull final Consumer<Integer> consumer) {
        connect(textInputControl, consumer, Integer::valueOf);
    }

    public static void connectDouble(@NotNull final TextInputControl textInputControl,
                                     @NotNull final Consumer<Double> consumer) {
        connect(textInputControl, consumer, Double::valueOf);
    }

    public static <T> void connect(@NotNull final TextInputControl textInputControl,
                                   @NotNull final Consumer<T> consumer,
                                   @NotNull final Function<String, T> function) {
        textInputControl.textProperty().addListener((observable, oldValue, newValue) ->
                executeSafe(() -> consumer.accept(function.apply(newValue))));
    }

}
