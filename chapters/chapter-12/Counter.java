public class Counter {
    public static void main(String[] args) {
        Booking b1 = new Booking("Smith", 2);
        Booking b2 = new Booking("Johnson", 4);
        Booking b3 = new Booking("Brown", 1);
        System.out.println(b1 + "\n" + b2 + "\n" + b3);
        System.out.println("Bookings created: " + Booking.created);
    }
}
class Booking {
    static int created = 0;
    final int code;
    final String customer;
    int seats;
    Booking(String customer, int seats) {
        created++;
        this.code = 1000 + created;
        this.customer = customer;
        this.seats = seats;
    }
    public String toString() { return "#" + code + " " + customer + " (" + seats + " seats)"; }
}
