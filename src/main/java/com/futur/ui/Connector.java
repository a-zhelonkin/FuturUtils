package com.futur.ui;

import com.futur.common.helpers.StringHelper;
import com.google.common.base.Strings;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.futur.common.helpers.DevelopmentHelper.executeSafe;

@SuppressWarnings("unused")
public final class Connector {

    private Connector() {
        StringHelper.throwNonInitializeable();
    }

    public static void connectInteger(@NotNull final TextInputControl textInputControl,
                                      @NotNull final Consumer<Integer> consumer) {
        connectNumeric(textInputControl, consumer, Integer::valueOf);
    }

    public static void connectDouble(@NotNull final TextInputControl textInputControl,
                                     @NotNull final Consumer<Double> consumer) {
        connectNumeric(textInputControl, consumer, Double::valueOf);
    }

    public static <T extends Number> void connectNumeric(@NotNull final TextInputControl textInputControl,
                                                         @NotNull final Consumer<T> consumer,
                                                         @NotNull final Function<String, T> function) {
        textInputControl.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Strings.isNullOrEmpty(newValue)) {
                executeSafe(() -> consumer.accept(function.apply(newValue)));
            }
        });
    }

    public static void setValueQuietFormatter(@NotNull final TextInputControl textInputControl,
                                              @NotNull final Object value) {
        setValueQuietFormatter(textInputControl, value.toString());
    }

    public static void setValueQuietFormatter(@NotNull final TextInputControl textInputControl,
                                              @NotNull final String value) {
        @Nullable final TextFormatter<?> textFormatter = textInputControl.getTextFormatter();
        textInputControl.setTextFormatter(null);
        textInputControl.setText(value);
        textInputControl.setTextFormatter(textFormatter);
    }

}
