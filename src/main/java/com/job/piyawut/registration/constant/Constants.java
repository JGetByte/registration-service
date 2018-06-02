package com.job.piyawut.registration.constant;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Piyawut Chiradejnunt<pchiradejnunt@gmail.com>
 */
public class Constants {

    public final static double PLATINUM_MEMBER_LIMIT = 50001d;
    public final static double GOLD_MEMBER_LIMIT = 30001d;
    public final static double SILVER_MEMBER_LIMIT = 15001d;

    public static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter
            .ofPattern("yyyyMMdd")
            .withZone(ZoneId.of("UTC"));
}
