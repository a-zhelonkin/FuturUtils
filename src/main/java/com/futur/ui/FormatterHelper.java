package com.futur.ui;

import com.futur.common.helpers.StringHelper;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;

@SuppressWarnings("unused")
public final class FormatterHelper {

    public FormatterHelper() {
        StringHelper.throwNonInitializeable();
    }

    public static void applyIntegerFormat(@NotNull TextInputControl textInputControl) {
        textInputControl.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textInputControl.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public static void applyDoubleFormat(@NotNull TextInputControl textInputControl) {
        textInputControl.setTextFormatter(new DoubleFormatter());
    }

    public static void applyDoubleFormat(@NotNull TextInputControl textInputControl, int decimal) {
        textInputControl.setTextFormatter(new DoubleFormatter(decimal));
    }

    private static class DoubleFormatter extends TextFormatter<Double> {

        @NotNull
        private static final DecimalFormat format;

        static {
            @NotNull final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
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

    public static void setNumeric(@NotNull TextInputControl textInputControl, @NotNull Number number) {
        textInputControl.setText(StringHelper.numberToString(number));
    }

}
