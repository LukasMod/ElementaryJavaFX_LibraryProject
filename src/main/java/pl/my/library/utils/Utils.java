package pl.my.library.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

//narzędzia do pracy z Converterami
public class Utils {

    public static Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
