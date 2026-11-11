import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Record {
    public static void main(String[] args) {
        Movie f = new Movie("A Night in the City", 2025, 112);
        Movie g = new Movie("A Night in the City", 2025, 112);
        System.out.println(f);
        System.out.println(f.title() + " " + f.year() + " " + f.lengthInHours());
        System.out.println((f == g) + " " + f.equals(g) + " " + (f.hashCode() == g.hashCode()));
        Set<Movie> seen = new HashSet<>();
        seen.add(f); seen.add(g);
        System.out.println("Distinct movies: " + seen.size());
        Movie r = f.withTitle("A Night in the City (restored)");
        System.out.println(r);
        Seat p = Seat.from("C5");
        System.out.println(p + " " + p.row() + " " + p.number());
        try { new Movie("", 2025, 90); } catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
        try { new Seat(-1, 3); } catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
        Schedule sched = new Schedule("Screen 1", new int[]{1530, 1800});
        int[] t = sched.times(); t[0] = 0;
        System.out.println(Arrays.toString(sched.times()));
        System.out.println(Movie.MIN_YEAR);
    }
}

record Movie(String title, int year, int lengthMinutes) {
    static final int MIN_YEAR = 1895;

    Movie {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("title is required");
        if (year < MIN_YEAR) throw new IllegalArgumentException("invalid year: " + year);
        title = title.strip();
    }

    String lengthInHours() { return lengthMinutes / 60 + "h " + lengthMinutes % 60 + "min"; }

    Movie withTitle(String newTitle) { return new Movie(newTitle, year, lengthMinutes); }
}

record Seat(int row, int number) {
    Seat {
        if (row < 0 || number < 0) throw new IllegalArgumentException("invalid seat: " + row + "," + number);
    }
    static Seat from(String code) {
        return new Seat(code.charAt(0) - 'A', Integer.parseInt(code.substring(1)) - 1);
    }
    @Override public String toString() { return "" + (char) ('A' + row) + (number + 1); }
}

record Schedule(String screen, int[] times) {
    Schedule { times = times.clone(); }
    @Override public int[] times() { return times.clone(); }
}
