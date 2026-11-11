public class Static {
    public static void main(String[] args) {
        Booking b1 = new Booking("Smith", 2);
        Booking b2 = new Booking("Johnson", 4);
        Booking b3 = new Booking("Brown", 1);
        System.out.println(b1 + "\n" + b2 + "\n" + b3);
        System.out.println("Bookings created: " + Booking.getTotalCreated());
        System.out.println(Booking.MAX_SEATS + " " + Math.PI + " " + Integer.MAX_VALUE);
        System.out.println(Conversions.minutesToHours(112));
    }
}
class Booking {
    static final int MAX_SEATS = 10;
    private static int created = 0;
    private final int code;
    private final String customer;
    private final int seats;
    Booking(String customer, int seats) {
        if (seats < 1 || seats > MAX_SEATS) throw new IllegalArgumentException("seats: " + seats);
        created++;
        this.code = 1000 + created;
        this.customer = customer;
        this.seats = seats;
    }
    static int getTotalCreated() { return created; }
    @Override public String toString() { return "#" + code + " " + customer + " (" + seats + (seats == 1 ? " seat)" : " seats)"); }
}
final class Conversions {
    private Conversions() {}
    static String minutesToHours(int m) { return m / 60 + "h " + m % 60 + "min"; }
}
