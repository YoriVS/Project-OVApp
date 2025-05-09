package org.example.ovapp.handler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class TimeHandler {
    public static final DateTimeFormatter FLEXIBLE_RFC3339_PARSER =
            new DateTimeFormatterBuilder()
                    .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    .appendPattern("[XXX][XX][X]")
                    .toFormatter();

    public static final DateTimeFormatter RFC3339_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    public static final DateTimeFormatter HH_MM_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static OffsetDateTime getCurrentRFC3339Time() {
        return OffsetDateTime.now();
    }

    public static String convertTimeToRFC3339(String timeString) {
        String[] parts = timeString.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime specificTime = localDateTime.withHour(hour).withMinute(minute).withSecond(0).withNano(0);

        ZoneId zoneId = ZoneId.systemDefault();
        OffsetDateTime offsetDateTime = specificTime.atZone(zoneId).toOffsetDateTime();
        return offsetDateTime.format(RFC3339_FORMATTER);
    }

    public static String extractHourMinute(String rfc3339Time) {
        OffsetDateTime dateTime = OffsetDateTime.parse(rfc3339Time, FLEXIBLE_RFC3339_PARSER);
        return dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));

    }
}
