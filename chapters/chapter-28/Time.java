import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.*;

public class Time {
    public static void main(String[] args) {
        LocalDate premiere = LocalDate.of(2026, 11, 14);
        LocalTime start = LocalTime.of(20, 30);
        LocalDateTime show = LocalDateTime.of(premiere, start);
        System.out.println(premiere + " " + start + " " + show);
        System.out.println(premiere.getDayOfWeek() + " " + premiere.getDayOfMonth() + " " + premiere.getMonth() + " " + premiere.getMonthValue() + " " + premiere.getDayOfYear() + " " + premiere.isLeapYear() + " " + premiere.lengthOfMonth());
        System.out.println(premiere.plusDays(30) + " " + premiere.minusMonths(1) + " " + premiere.plusWeeks(2) + " " + premiere.withDayOfMonth(1) + " " + premiere.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)) + " " + premiere.with(TemporalAdjusters.lastDayOfMonth()));
        System.out.println(LocalDate.of(2026, 1, 31).plusMonths(1) + " " + LocalDate.of(2028, 1, 31).plusMonths(1));
        LocalTime end = start.plusMinutes(112);
        System.out.println(end + " " + LocalTime.of(23, 0).plusMinutes(128) + " " + start.isBefore(end));
        try { LocalDate.of(2026, 2, 30); } catch (DateTimeException e) { System.out.println(e.getMessage()); }
        Duration d = Duration.ofMinutes(112);
        System.out.println(d + " " + d.toHours() + " " + d.toMinutesPart() + " " + Duration.between(start, end).toMinutes());
        Period p = Period.between(LocalDate.of(1998, 3, 12), LocalDate.of(2026, 10, 25));
        System.out.println(p + " " + p.getYears() + " years");
        System.out.println(ChronoUnit.DAYS.between(LocalDate.of(2026, 10, 25), premiere) + " days to the premiere");

        DateTimeFormatter us = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy 'at' h:mm a", Locale.US);
        System.out.println(show.format(us));
        DateTimeFormatter shortUs = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println(premiere.format(shortUs) + " " + LocalDate.parse("11/14/2026", shortUs) + " " + LocalDate.parse("2026-11-14") + " " + LocalTime.parse("20:30"));
        System.out.println(premiere.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.US)) + " | " + premiere.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.UK)));
        try { LocalDate.parse("11/14/26", shortUs); } catch (DateTimeParseException e) { System.out.println(e.getMessage()); }
        System.out.println(premiere.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US) + " " + Month.NOVEMBER.getDisplayName(TextStyle.SHORT, Locale.US));
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.of(2026, 12, 31)) + " " + DateTimeFormatter.ofPattern("YYYY-MM-dd").format(LocalDate.of(2026, 12, 31)));
        List<LocalDate> dates = new ArrayList<>(List.of(premiere, premiere.minusDays(3), premiere.plusDays(1)));
        Collections.sort(dates);
        System.out.println(dates);
        System.out.println(LocalDate.of(2026, 11, 1).datesUntil(LocalDate.of(2026, 12, 1)).filter(x -> x.getDayOfWeek() == DayOfWeek.WEDNESDAY).toList());
    }
}
