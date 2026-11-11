import java.util.EnumMap;
import java.util.Map;

public class Enum {
    public static void main(String[] args) {
        Day today = Day.WED;
        System.out.println(today + " " + today.name() + " " + today.ordinal() + " " + today.getName());
        System.out.println(Day.valueOf("SAT").isWeekend() + " " + Day.values().length);
        for (Rate r : Rate.values()) {
            System.out.printf("%-8s $%5.2f  %s%n", r, r.price(950) / 100.0, r.getDescription());
        }
        System.out.println(Rate.REDUCED.compareTo(Rate.VIP) + " " + (today == Day.WED));
        String msg = switch (today) {
            case SAT, SUN -> "weekend";
            case WED -> "reduced-price day";
            default -> "weekday";
        };
        System.out.println(msg);
        Map<Day, Integer> viewers = new EnumMap<>(Day.class);
        viewers.put(Day.SAT, 890);
        viewers.put(Day.MON, 312);
        System.out.println(viewers);
        try { Day.valueOf("saturday"); } catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
        System.out.println(State.BOOKED.canMoveTo(State.PAID) + " " + State.CANCELLED.canMoveTo(State.PAID));
    }
}

enum Day {
    MON("Monday"), TUE("Tuesday"), WED("Wednesday"), THU("Thursday"),
    FRI("Friday"), SAT("Saturday"), SUN("Sunday");

    private final String name;
    Day(String name) { this.name = name; }
    String getName() { return name; }
    boolean isWeekend() { return this == SAT || this == SUN; }
}

enum Rate {
    FULL("full price") {
        @Override long price(long base) { return base; }
    },
    REDUCED("under 14 and over 65") {
        @Override long price(long base) { return base - 250; }
    },
    VIP("leather seat") {
        @Override long price(long base) { return base + 400; }
    };

    private final String description;
    Rate(String description) { this.description = description; }
    String getDescription() { return description; }
    abstract long price(long base);
}

enum State {
    BOOKED, PAID, CANCELLED;
    boolean canMoveTo(State next) {
        return switch (this) {
            case BOOKED -> next == PAID || next == CANCELLED;
            case PAID -> next == CANCELLED;
            case CANCELLED -> false;
        };
    }
}
