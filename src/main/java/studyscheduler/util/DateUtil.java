package studyscheduler.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");

    public static String format(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("Data não pode ser nula");
        }
        return dateTime.format(FORMATTER);
    }

    public static String formatDateOnly(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Data não pode ser nula");
        }
        return date.toString().replace("-", "") + "T120000";
    }

    public static String formatWithFallback(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "20260420T120000";
        }
        return dateTime.format(FORMATTER);
    }
}