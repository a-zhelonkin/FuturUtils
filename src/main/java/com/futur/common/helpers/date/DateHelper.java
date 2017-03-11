package com.futur.common.helpers.date;

import com.futur.common.helpers.StringHelper;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import static java.time.temporal.ChronoField.*;

public final class DateHelper {

    @NotNull
    private static final DateTimeFormatter DATA_TIME_FORMATTER;

    @NotNull
    public static final Set<String> NEAR_TEN_YEARS;

    public static final int CURRENT_YEAR;
    public static final int CURRENT_MONTH;
    public static final int CURRENT_DAY;

    static {
        @NotNull GregorianCalendar gregorianCalendar = new GregorianCalendar();

        CURRENT_YEAR = gregorianCalendar.get(Calendar.YEAR);
        CURRENT_MONTH = gregorianCalendar.get(Calendar.MONTH) + 1;
        CURRENT_DAY = gregorianCalendar.get(Calendar.DAY_OF_MONTH);

        DATA_TIME_FORMATTER = new DateTimeFormatterBuilder()
                .appendLiteral('_')
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .appendLiteral('_')
                .appendValue(HOUR_OF_DAY, 2)
                .appendLiteral('-')
                .appendValue(MINUTE_OF_HOUR, 2)
                .appendLiteral('-')
                .appendValue(SECOND_OF_MINUTE, 2)
                .appendLiteral('-')
                .appendValue(MILLI_OF_SECOND, 3)
                .toFormatter();

        NEAR_TEN_YEARS = ImmutableSet.of(
                Integer.toString(CURRENT_YEAR + 5),
                Integer.toString(CURRENT_YEAR + 4),
                Integer.toString(CURRENT_YEAR + 3),
                Integer.toString(CURRENT_YEAR + 2),
                Integer.toString(CURRENT_YEAR + 1),
                Integer.toString(CURRENT_YEAR),
                Integer.toString(CURRENT_YEAR - 1),
                Integer.toString(CURRENT_YEAR - 2),
                Integer.toString(CURRENT_YEAR - 3),
                Integer.toString(CURRENT_YEAR - 4),
                Integer.toString(CURRENT_YEAR - 5)
        );
    }

    private DateHelper() {
        StringHelper.throwNonInitializeable();
    }

    @NotNull
    public static String now() {
        return ZonedDateTime.now().format(DATA_TIME_FORMATTER);
    }

}
