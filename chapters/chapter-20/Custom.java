public class Custom {
    public static void main(String[] args) {
        Screen screen = new Screen("Screen 1", 10);
        try {
            screen.book(4);
            screen.book(8);
        } catch (SeatsUnavailableException e) {
            System.out.println(e.getMessage() + " (requested " + e.getRequested() + ", free " + e.getFree() + ")");
        }
        try {
            screen.book(-2);
        } catch (SeatsUnavailableException e) {
            System.out.println("never gets here");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid argument: " + e.getMessage());
        }
        try {
            PriceList.load("prices.csv");
        } catch (PriceListException e) {
            System.out.println(e.getMessage() + " <- cause: " + e.getCause());
        }
        PriceList.load("prices.csv");
    }
}

class SeatsUnavailableException extends Exception {
    private final int requested, free;
    SeatsUnavailableException(String screen, int requested, int free) {
        super("Seats unavailable in " + screen);
        this.requested = requested;
        this.free = free;
    }
    int getRequested() { return requested; }
    int getFree() { return free; }
}

class Screen {
    private final String name; private final int capacity; private int sold;
    Screen(String name, int capacity) { this.name = name; this.capacity = capacity; }
    void book(int seats) throws SeatsUnavailableException {
        if (seats <= 0) throw new IllegalArgumentException("seats must be positive: " + seats);
        int free = capacity - sold;
        if (seats > free) throw new SeatsUnavailableException(name, seats, free);
        sold += seats;
    }
}

class PriceListException extends RuntimeException {
    PriceListException(String msg, Throwable cause) { super(msg, cause); }
}

class PriceList {
    static void load(String file) {
        try {
            java.nio.file.Files.readAllLines(java.nio.file.Path.of(file));
        } catch (java.io.IOException e) {
            throw new PriceListException("Cannot load the price list " + file, e);
        }
    }
}
