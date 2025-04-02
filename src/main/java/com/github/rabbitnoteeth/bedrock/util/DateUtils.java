package com.github.rabbitnoteeth.bedrock.util;

import com.github.rabbitnoteeth.bedrock.util.exception.DateFormatException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

    private DateUtils() {
    }

    public static String now() {
        return DateUtils.formatLocalDateTime(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss");
    }

    public static Date parseDate(String str, String pattern) throws DateFormatException {
        try {
            return new SimpleDateFormat(pattern).parse(str);
        } catch (Throwable e) {
            throw new DateFormatException(e);
        }
    }

    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parseLocalDateTime(String dateStr, String pattern) {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatDate(Date time, String pattern) {
        return new SimpleDateFormat(pattern).format(time);
    }

    public static String formatLocalDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalDateTime(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate convertDate2LocalDate(Date date, String pattern) {
        String dateStr = new SimpleDateFormat(pattern).format(date);
        return parseLocalDate(dateStr, pattern);
    }

    public static LocalDateTime convertDate2LocalDateTime(Date date, String pattern) {
        String dateStr = new SimpleDateFormat(pattern).format(date);
        return parseLocalDateTime(dateStr, pattern);
    }

    public static Long getSecondTimestamp() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
    }

    public static Long getMilliSecondTimestamp() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public static Long getNanoTimestamp() {
        return System.nanoTime();
    }

    public static Long intervalDaysOfLocalDate(String startDate, String endDate, String pattern) {
        LocalDate localStartDate = parseLocalDate(startDate, pattern);
        LocalDate localEndDate = parseLocalDate(endDate, pattern);

        return localStartDate.until(localEndDate, ChronoUnit.DAYS);
    }

    public static List<String> intervalDayList(String startDate, String endDate) {
        List<String> days = new ArrayList<>();
        DateFormat dateFormat = DateFormat.getDateInstance();

        Date parseStartDate;
        Date parseEndDate;
        try {
            parseStartDate = dateFormat.parse(startDate);
            parseEndDate = dateFormat.parse(endDate);

            Calendar start = Calendar.getInstance();
            start.setTime(parseStartDate);
            Calendar end = Calendar.getInstance();
            end.setTime(parseEndDate);

            while (start.before(end) || start.equals(end)) {
                String format = dateFormat.format(start.getTime());
                String[] split = format.split("-");

                if (Integer.valueOf(split[1]) < 10) {
                    split[1] = "0" + split[1];
                }
                if (Integer.valueOf(split[2]) < 10) {
                    split[2] = "0" + split[2];
                }
                days.add(split[0] + "-" + split[1] + "-" + split[2]);
                start.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }

    public static Long intervalDaysOfLocalDateTime(String startTime, String endTime, String pattern) {
        LocalDateTime localStartTime = parseLocalDateTime(startTime, pattern);
        LocalDateTime localEndTime = parseLocalDateTime(endTime, pattern);
        return localStartTime.until(localEndTime, ChronoUnit.DAYS);
    }

    public static Long intervalHoursOfLocalDateTime(String startTime, String endTime, String pattern) {
        LocalDateTime localStartTime = parseLocalDateTime(startTime, pattern);
        LocalDateTime localEndTime = parseLocalDateTime(endTime, pattern);
        return localStartTime.until(localEndTime, ChronoUnit.HOURS);
    }

}
