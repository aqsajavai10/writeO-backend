package org.writeo.utils.CommonHelper;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.writeo.utils.CommonConstants.CommonConstants;

public final class CommonDateHelper {
    private CommonDateHelper() {

    }


    public static Date dateFormatInUTC(Object dateObject) {
        if (dateObject instanceof String) {

            String dateString = (String) dateObject;
            LocalDateTime localDateTime = LocalDateTime.parse(dateString, CommonConstants.utcDateFormatter);
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());


        } else if (dateObject instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) dateObject;
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } else if (dateObject instanceof Date) {
            Date date = (Date) dateObject;
            LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        }
        return null; // Or throw an exception for unsupported types
    }

    public static String dateFormatInISO(Object dateObject) {
        String dateString =  dateObject.toString();
        return ZonedDateTime.of(
                LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(CommonConstants.dateFormatInUtc)),
                ZoneId.of("America/New_York")).format(DateTimeFormatter.ISO_LOCAL_DATE);
        // Or throw an exception for unsupported types
    }
}
