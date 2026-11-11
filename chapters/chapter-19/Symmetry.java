import java.util.Objects;
public class Symmetry {
    public static void main(String[] args) {
        Ticket t = new Ticket("Skyline", 950);
        VipTicket v = new VipTicket("Skyline", 950, "F12");
        System.out.println(t.equals(v) + " " + v.equals(t));
    }
}
class Ticket {
    final String movie; final long price;
    Ticket(String m, long p) { movie = m; price = p; }
    @Override public boolean equals(Object o) {
        return o instanceof Ticket x && x.movie.equals(movie) && x.price == price;
    }
    @Override public int hashCode() { return Objects.hash(movie, price); }
}
class VipTicket extends Ticket {
    final String seat;
    VipTicket(String m, long p, String s) { super(m, p); seat = s; }
    @Override public boolean equals(Object o) {
        return o instanceof VipTicket x && super.equals(x) && x.seat.equals(seat);
    }
    @Override public int hashCode() { return Objects.hash(super.hashCode(), seat); }
}
