import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class Nested {
    public static void main(String[] args) {
        Screen.Stats s = new Screen.Stats(120, 95);
        System.out.println(s + " " + s.percentage());

        Screen screen = new Screen("Screen 1", 3, 4);
        Screen.Seat p = screen.new Seat(1, 2);
        p.book();
        screen.seat(0, 0).book();
        System.out.println(p + " " + p.isTaken() + " " + screen.countTaken());

        for (Screen.Seat x : screen) {
            if (x.isTaken()) System.out.print(x + " ");
        }
        System.out.println();

        String[] movies = {"Skyline", "The Big River", "A Night in the City"};
        Arrays.sort(movies, new Comparator<String>() {
            @Override public int compare(String a, String b) { return Integer.compare(a.length(), b.length()); }
        });
        System.out.println(Arrays.toString(movies));

        int threshold = 10;
        record Line(String title, int length) {}
        class Filter {
            boolean isLong(String t) { return t.length() > threshold; }
        }
        Filter f = new Filter();
        for (String t : movies) if (f.isLong(t)) System.out.print(new Line(t, t.length()) + " ");
        System.out.println();
    }
}

class Screen implements Iterable<Screen.Seat> {
    private final String name;
    private final boolean[][] taken;

    Screen(String name, int rows, int seatsPerRow) {
        this.name = name;
        this.taken = new boolean[rows][seatsPerRow];
    }

    Seat seat(int row, int number) { return new Seat(row, number); }

    int countTaken() {
        int n = 0;
        for (boolean[] r : taken) for (boolean b : r) if (b) n++;
        return n;
    }

    static class Stats {
        private final int capacity, sold;
        Stats(int capacity, int sold) { this.capacity = capacity; this.sold = sold; }
        String percentage() { return String.format("%.1f%%", 100.0 * sold / capacity); }
        @Override public String toString() { return sold + "/" + capacity; }
    }

    class Seat {
        private final int row, number;
        Seat(int row, int number) { this.row = row; this.number = number; }
        void book() { taken[row][number] = true; }
        boolean isTaken() { return taken[row][number]; }
        @Override public String toString() { return name + "/" + (char) ('A' + row) + (number + 1); }
    }

    @Override
    public Iterator<Seat> iterator() {
        return new Iterator<>() {
            private int i = 0;
            private final int total = taken.length * taken[0].length;
            @Override public boolean hasNext() { return i < total; }
            @Override public Seat next() {
                int c = taken[0].length;
                Seat p = new Seat(i / c, i % c);
                i++;
                return p;
            }
        };
    }
}
