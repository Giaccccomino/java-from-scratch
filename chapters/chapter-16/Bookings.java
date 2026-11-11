public class Bookings {
    static int bookEverywhere(Bookable[] resources, int seats) {
        int succeeded = 0;
        for (Bookable r : resources) {
            if (r.book(seats)) succeeded++;
        }
        return succeeded;
    }

    public static void main(String[] args) {
        Screen screen = new Screen("Screen 1", 120);
        ParkingLot park = new ParkingLot(40);
        BarTable table = new BarTable(4);
        Bookable[] resources = { screen, park, table };
        System.out.println("Succeeded: " + bookEverywhere(resources, 3));
        System.out.println("Succeeded: " + bookEverywhere(resources, 3));
        for (Bookable r : resources) {
            System.out.println(r.description() + " -> free: " + r.freeSeats() + (r.isFull() ? " (full)" : ""));
        }
        Printable p = screen;
        System.out.println(p.header());
        System.out.println(Bookable.MAX_PER_REQUEST + " " + Bookable.isValid(12));
    }
}

interface Bookable {
    int MAX_PER_REQUEST = 10;

    boolean book(int seats);

    int freeSeats();

    default boolean isFull() {
        return freeSeats() == 0;
    }

    default String description() {
        return getClass().getSimpleName();
    }

    static boolean isValid(int seats) {
        return seats > 0 && seats <= MAX_PER_REQUEST;
    }
}

interface Printable {
    String text();

    default String header() {
        return frame() + "\n" + text() + "\n" + frame();
    }

    private String frame() {
        return "=".repeat(text().length());
    }
}

class Screen implements Bookable, Printable {
    private final String name;
    private final int capacity;
    private int sold;

    Screen(String name, int capacity) { this.name = name; this.capacity = capacity; }

    @Override
    public boolean book(int seats) {
        if (!Bookable.isValid(seats) || seats > freeSeats()) return false;
        sold += seats;
        return true;
    }

    @Override public int freeSeats() { return capacity - sold; }
    @Override public String description() { return name; }
    @Override public String text() { return "AURORA CINEMA - " + name; }
}

class ParkingLot implements Bookable {
    private int free;
    ParkingLot(int spaces) { free = spaces; }
    @Override public boolean book(int seats) {
        if (seats != 1 || free == 0) return false;
        free--;
        return true;
    }
    @Override public int freeSeats() { return free; }
}

class BarTable implements Bookable {
    private final int chairs;
    private boolean taken;
    BarTable(int chairs) { this.chairs = chairs; }
    @Override public boolean book(int people) {
        if (taken || people > chairs) return false;
        taken = true;
        return true;
    }
    @Override public int freeSeats() { return taken ? 0 : chairs; }
}
