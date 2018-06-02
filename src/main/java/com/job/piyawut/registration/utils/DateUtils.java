package com.job.piyawut.registration.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Piyawut Chiradejnunt<pchiradejnunt@gmail.com>
 */
public class DateUtils {

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter
            .ofPattern("yyyyMMdd")
            .withZone(ZoneId.of("UTC"));

    public static String currentDate() {
        return DATE_FORMAT.format(currentLocalDate());
    }

    public static LocalDate currentLocalDate() {
        return currentDateByUTC("UTC+7");
    }

    public static LocalDate currentDateByUTC(String utc) {
        return LocalDate.now(ZoneId.of(utc));
    }

}
