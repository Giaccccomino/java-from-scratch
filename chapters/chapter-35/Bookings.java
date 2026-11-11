import java.util.*;

public class Bookings {
    static class BookingException extends Exception {
        BookingException(String msg, Throwable cause) { super(msg, cause); }
    }

    static Map<String, Integer> seats = new HashMap<>();

    static int freeSeats(String screen) {
        return seats.get(screen);          // line 11
    }

    static void book(String screen, int n) throws BookingException {
        try {
            int free = freeSeats(screen);   // line 16
            if (free < n) throw new IllegalStateException("not enough seats");
            seats.put(screen, free - n);
        } catch (RuntimeException e) {
            throw new BookingException("booking failed for " + screen, e);  // line 20
        }
    }

    public static void main(String[] args) throws Exception {
        seats.put("Screen 1", 120);
        book("Screen 1", 4);
        book("Screen 7", 2);   // line 27
    }
}
