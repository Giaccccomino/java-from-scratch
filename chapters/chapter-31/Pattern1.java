import java.util.*;

public class Pattern1 {
    sealed interface Payment permits Cash, Card, Voucher {}
    record Cash(long amount, long given) implements Payment {}
    record Card(long amount, String network, String last4) implements Payment {}
    record Voucher(long amount, String code, boolean complimentary) implements Payment {}

    static String describe(Object o) {
        return switch (o) {
            case null -> "nothing";
            case Integer i when i > 100 -> "a big integer: " + i;
            case Integer i -> "an integer: " + i;
            case String s when s.isBlank() -> "a blank string";
            case String s -> "the string \"" + s + "\" of " + s.length() + " characters";
            case int[] a -> "an array of " + a.length + " ints";
            case List<?> l -> "a list of " + l.size() + " elements";
            default -> "a " + o.getClass().getSimpleName();
        };
    }

    static String receipt(Payment p) {
        return switch (p) {
            case Cash(long amount, long given) when given < amount -> "ERROR: not enough cash";
            case Cash(long amount, long given) -> String.format("Cash %.2f, change %.2f", amount / 100.0, (given - amount) / 100.0);
            case Card(var amount, var network, var last4) -> network + " **** " + last4;
            case Voucher(var amount, var code, var complimentary) when complimentary -> "Complimentary " + code;
            case Voucher v -> "Voucher " + v.code();
        };
    }

    record Seat(char row, int number) {}
    record Booking(String customer, Seat seat, Payment payment) {}

    static String check(Booking b) {
        return switch (b) {
            case Booking(var c, Seat(char r, int n), Card(var amt, var net, var l)) when r == 'A' -> c + ": front row paid with " + net;
            case Booking(var c, Seat(var r, var n), Voucher v) -> c + ": seat " + r + n + " with a voucher";
            case Booking(var c, var seat, var pay) -> c + ": " + seat;
        };
    }

    public static void main(String[] args) {
        for (Object o : new Object[]{null, 7, 250, "  ", "Skyline", new int[3], List.of(1, 2), 3.5})
            System.out.println(describe(o));
        for (Payment p : List.<Payment>of(new Cash(1900, 2000), new Cash(1900, 1000), new Card(2700, "Visa", "4821"), new Voucher(950, "AUR-77", true), new Voucher(950, "AUR-78", false)))
            System.out.println(receipt(p));
        System.out.println(check(new Booking("Smith", new Seat('A', 5), new Card(950, "Mastercard", "1111"))));
        System.out.println(check(new Booking("Johnson", new Seat('C', 7), new Voucher(950, "X", false))));
        System.out.println(check(new Booking("Brown", new Seat('B', 2), new Cash(950, 1000))));
        Object o = new Seat('D', 9);
        if (o instanceof Seat(char r, int n) && n > 5) System.out.println("row " + r + ", high seat " + n);
        String s = null;
        try { switch (s) { case "a" -> System.out.println("a"); default -> System.out.println("other"); } } catch (NullPointerException e) { System.out.println("NPE without case null"); }
    }
}
