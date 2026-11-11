public class Tickets {
    public static void main(String[] args) {
        Ticket t1 = new Ticket("A Night in the City", 950);
        ReducedTicket t2 = new ReducedTicket("A Night in the City", 950, "under 14");
        VipTicket t3 = new VipTicket("A Night in the City", 950, "F12");
        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);
        System.out.println(t2.getMovie() + " " + t3.getSeat());
        Ticket[] sold = { t1, t2, t3, new ReducedTicket("The Big River", 950, "over 65") };
        long takings = 0;
        for (Ticket t : sold) takings += t.getPriceCents();
        System.out.printf("Takings: $%.2f%n", takings / 100.0);
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
        return String.format("%-8s %-20s %6.2f", type(), movie, getPriceCents() / 100.0);
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
