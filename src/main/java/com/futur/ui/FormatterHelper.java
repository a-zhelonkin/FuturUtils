package com.futur.ui;

import com.futur.common.helpers.DevelopmentHelper;
import com.futur.common.helpers.StringHelper;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("unused")
public final class FormatterHelper {

    private FormatterHelper() {
        StringHelper.throwNonInitializeable();
    }

    public static void applyIntegerFormat(@NotNull final TextInputControl textInputControl) {
        textInputControl.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textInputControl.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public static void applyDoubleFormat(@NotNull final TextInputControl textInputControl) {
        textInputControl.setTextFormatter(new DoubleFormatter());
    }

    public static void applyDoubleFormat(@NotNull final TextInputControl textInputControl, int decimal) {
        textInputControl.setTextFormatter(new DoubleFormatter(decimal));
    }

    private static class DoubleFormatter extends TextFormatter<Double> {

        @NotNull
        private static final DecimalFormat format;

        static {
            @NotNull val decimalFormatSymbols = new DecimalFormatSymbols();
            decimalFormatSymbols.setDecimalSeparator('.');
            format = new DecimalFormat("#.0", decimalFormatSymbols);
        }

        private DoubleFormatter() {
            this(null);
        }

        private DoubleFormatter(final int decimal) {
            this("%." + decimal + "f");
        }

        private DoubleFormatter(@Nullable final String decimalFormat) {
            super(c -> {
                        if (c.getControlNewText().isEmpty()) {
                            return c;
                        }

                        @NotNull final ParsePosition parsePosition = new ParsePosition(0);
                        @NotNull final Number number = format.parse(c.getControlNewText(), parsePosition);

                        if (number == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                            return null;
                        } else {
                            if (decimalFormat != null) {
                                c.setText(String.format(decimalFormat, number.doubleValue()));
                            }
                            return c;
                        }
                    }
            );
        }
    }

    public static void setNumeric(@NotNull final Labeled labeled, @NotNull final Number number) {
        labeled.setText(StringHelper.numberToString(number));
    }

    public static void setNumeric(@NotNull final TextInputControl textInputControl, @NotNull final Number number) {
        textInputControl.setText(StringHelper.numberToString(number));
    }

    @NotNull
    public static Integer getInteger(@NotNull final TextInputControl textInputControl) {
        return getNumeric(textInputControl, Integer::valueOf);
    }

    @NotNull
    public static Integer getInteger(@NotNull final TextInputControl textInputControl,
                                     @NotNull final Integer defaultValue) {
        return getNumeric(textInputControl, Integer::valueOf, defaultValue);
    }

    public static void getInteger(@NotNull final TextInputControl textInputControl,
                                  @NotNull final Consumer<Integer> setter) {
        getNumeric(textInputControl, Integer::valueOf, setter);
    }

    @NotNull
    public static Float getFloat(@NotNull final TextInputControl textInputControl) {
        return getNumeric(textInputControl, Float::valueOf);
    }

    @NotNull
    public static Float getFloat(@NotNull final TextInputControl textInputControl,
                                 @NotNull final Float defaultValue) {
        return getNumeric(textInputControl, Float::valueOf, defaultValue);
    }

    public static void getFloat(@NotNull final TextInputControl textInputControl,
                                @NotNull final Consumer<Float> setter) {
        getNumeric(textInputControl, Float::valueOf, setter);
    }

    @NotNull
    public static Double getDouble(@NotNull final TextInputControl textInputControl) {
        return getNumeric(textInputControl, Double::valueOf);
    }

    @NotNull
    public static Double getDouble(@NotNull final TextInputControl textInputControl,
                                   @NotNull final Double defaultValue) {
        return getNumeric(textInputControl, Double::valueOf, defaultValue);
    }

    public static void getDouble(@NotNull final TextInputControl textInputControl,
                                 @NotNull final Consumer<Double> setter) {
        getNumeric(textInputControl, Double::valueOf, setter);
    }

    @NotNull
    private static <T extends Number> T getNumeric(@NotNull final TextInputControl textInputControl,
                                                   @NotNull final Function<String, T> function) {
        return function.apply(textInputControl.getText());
    }

    @NotNull
    private static <T extends Number> T getNumeric(@NotNull final TextInputControl textInputControl,
                                                   @NotNull final Function<String, T> function,
                                                   @NotNull final T defaultValue) {
        try {
            return getNumeric(textInputControl, function);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private static <T extends Number> void getNumeric(@NotNull final TextInputControl textInputControl,
                                                      @NotNull final Function<String, T> function,
                                                      @NotNull final Consumer<T> setter) {
        DevelopmentHelper.executeSafe(() -> setter.accept(getNumeric(textInputControl, function)));
    }

}
