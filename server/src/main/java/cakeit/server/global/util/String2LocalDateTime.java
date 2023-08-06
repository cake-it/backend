package cakeit.server.global.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class String2LocalDateTime {

    public static LocalDateTime string2LocalDateTime(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(str, formatter);
        LocalDateTime localDateTime = localDate.atStartOfDay();

        return localDateTime;
    }
}
