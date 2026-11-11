public class Poly {
    static void printReceipt(Ticket[] tickets) {
        long total = 0;
        for (Ticket t : tickets) {
            System.out.println(t);
            total += t.getPriceCents();
        }
        System.out.printf("TOTAL %37.2f%n", total / 100.0);
    }

    public static void main(String[] args) {
        Ticket t = new VipTicket("Skyline", 950, "F12");
        System.out.println(t.getPriceCents() + " " + t.getClass().getSimpleName());
        Ticket[] order = {
            new Ticket("Skyline", 950),
            new ReducedTicket("Skyline", 950, "under 14"),
            new VipTicket("Skyline", 950, "F12")
        };
        printReceipt(order);
        for (Ticket x : order) {
            if (x instanceof VipTicket vip) {
                System.out.println("Show the guest to seat " + vip.getSeat());
            }
        }
        Object o = order[2];
        if (o instanceof VipTicket v && v.getSeat().startsWith("F")) {
            System.out.println("Row F");
        }
        VipTicket forced = (VipTicket) order[2];
        System.out.println(forced.getSeat());
        VipTicket wrong = (VipTicket) order[1];
    }
}

class Ticket {
    private final String movie;
    protected final long basePrice;

    Ticket(String movie, long basePrice) {
        this.movie = movie;
        this.basePrice = basePrice;
    }

    String getMovie() { return movie; }

    long getPriceCents() { return basePrice; }

    String type() { return "Full"; }

    @Override
    public String toString() {
        return String.format("%-8s %-16s %10.2f", type(), movie, getPriceCents() / 100.0);
    }
}

class ReducedTicket extends Ticket {
    private final String reason;

    ReducedTicket(String movie, long basePrice, String reason) {
        super(movie, basePrice);
        this.reason = reason;
    }

    @Override
    long getPriceCents() { return basePrice - 250; }

    @Override
    String type() { return "Reduced"; }

    @Override
    public String toString() { return super.toString() + "  (" + reason + ")"; }
}

class VipTicket extends Ticket {
    static final long SURCHARGE = 400;
    private final String seat;

    VipTicket(String movie, long basePrice, String seat) {
        super(movie, basePrice);
        this.seat = seat;
    }

    String getSeat() { return seat; }

    @Override
    long getPriceCents() { return super.getPriceCents() + SURCHARGE; }

    @Override
    String type() { return "VIP"; }
}
