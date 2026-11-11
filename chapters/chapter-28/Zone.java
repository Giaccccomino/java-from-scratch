import java.math.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class Zone {
    public static void main(String[] args) {
        System.out.println(Period.between(LocalDate.of(1998, 3, 12), LocalDate.of(2026, 10, 25)));
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.of(2025, 12, 29)) + " " + DateTimeFormatter.ofPattern("YYYY-MM-dd").format(LocalDate.of(2025, 12, 29)));
        ZoneId ny = ZoneId.of("America/New_York");
        ZonedDateTime premiere = ZonedDateTime.of(LocalDateTime.of(2026, 11, 14, 20, 30), ny);
        System.out.println(premiere + " | LA: " + premiere.withZoneSameInstant(ZoneId.of("America/Los_Angeles")) + " | London: " + premiere.withZoneSameInstant(ZoneId.of("Europe/London")) + " | Tokyo: " + premiere.withZoneSameInstant(ZoneId.of("Asia/Tokyo")).toLocalDateTime());
        ZonedDateTime sat = ZonedDateTime.of(LocalDateTime.of(2026, 10, 31, 23, 0), ny);
        ZonedDateTime sun = sat.plusHours(4);
        System.out.println(sat + " + 4 hours = " + sun);
        System.out.println(Duration.between(sat, ZonedDateTime.of(LocalDateTime.of(2026, 11, 1, 3, 0), ny)).toMinutes() + " minutes between 11 PM and 3 AM (the night the clocks change)");
        System.out.println(ZonedDateTime.of(LocalDateTime.of(2026, 3, 8, 2, 30), ny));
        System.out.println(sat.plusDays(1) + " vs " + sat.plus(Duration.ofHours(24)));
        Instant now = Instant.parse("2026-11-15T01:30:00Z");
        System.out.println(now + " " + now.atZone(ny) + " " + now.toEpochMilli());
        System.out.println(OffsetDateTime.of(2026, 11, 14, 20, 30, 0, 0, ZoneOffset.ofHours(-5)));

        System.out.println(0.1 + 0.2 + " " + (1.10 - 1.00) + " " + 3 * 9.95);
        BigDecimal price = new BigDecimal("9.50");
        BigDecimal total = price.multiply(BigDecimal.valueOf(3));
        System.out.println(total + " " + new BigDecimal(0.1) + " " + new BigDecimal("0.1") + " " + BigDecimal.valueOf(0.1));
        BigDecimal subtotal = new BigDecimal("28.50");
        BigDecimal tax = subtotal.multiply(new BigDecimal("0.08")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal withTax = subtotal.add(tax);
        BigDecimal net = withTax.divide(new BigDecimal("1.08"), 2, RoundingMode.HALF_UP);
        System.out.println("tax " + tax + ", with tax " + withTax + ", net " + net);
        try { BigDecimal.ONE.divide(new BigDecimal("3")); } catch (ArithmeticException e) { System.out.println(e.getMessage()); }
        System.out.println(new BigDecimal("2.0").equals(new BigDecimal("2.00")) + " " + new BigDecimal("2.0").compareTo(new BigDecimal("2.00")) + " " + new BigDecimal("2.50").stripTrailingZeros() + " " + new BigDecimal("1.005").setScale(2, RoundingMode.HALF_UP) + " " + new BigDecimal("1.005").setScale(2, RoundingMode.HALF_EVEN) + " " + new BigDecimal("1.015").setScale(2, RoundingMode.HALF_EVEN));
        java.text.NumberFormat dollars = java.text.NumberFormat.getCurrencyInstance(Locale.US);
        System.out.println(dollars.format(new BigDecimal("1234.5")) + " | " + String.format(Locale.US, "%,.2f", new BigDecimal("1234.5")) + " | " + java.text.NumberFormat.getCurrencyInstance(Locale.ITALY).format(new BigDecimal("1234.5")));
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < 10; i++) sum = sum.add(new BigDecimal("0.10"));
        double sd = 0; for (int i = 0; i < 10; i++) sd += 0.10;
        System.out.println(sum + " " + sd);
        BigDecimal bill = new BigDecimal("100.00");
        BigDecimal share = bill.divide(BigDecimal.valueOf(3), 2, RoundingMode.DOWN);
        BigDecimal remainder = bill.subtract(share.multiply(BigDecimal.valueOf(3)));
        System.out.println(share + " " + share + " " + share.add(remainder));
    }
}
