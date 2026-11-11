import java.util.*;
public class Copies {
    public static void main(String[] args) {
        Auditorium a = new Auditorium("Screen 1", 2, 3);
        a.book(0, 1);
        Auditorium c = a.clone();
        Auditorium k = new Auditorium(a);
        c.book(1, 1);
        k.book(1, 2);
        System.out.println(a + "\n" + c + "\n" + k);
        int[][] m = {{1, 2}, {3, 4}};
        int[][] shallow = m.clone();
        shallow[0][0] = 99;
        System.out.println(m[0][0]);
        Movie f1 = new Movie("Skyline", 128), f2 = new Movie("skyline", 128);
        System.out.println(f1.compareTo(f2) + " " + f1.equals(f2));
        TreeSet<Movie> ts = new TreeSet<>(List.of(new Movie("Skyline", 128), new Movie("The Secret Library", 128)));
        System.out.println(ts.size());
        System.out.println(Objects.requireNonNullElse(null, "untitled") + " " + Objects.toString(null, "-") + " " + Objects.hashCode(null));
        try { Objects.requireNonNull(null, "the title must not be null"); } catch (NullPointerException e) { System.out.println(e.getMessage()); }
    }
}
class Auditorium implements Cloneable {
    private final String name;
    private boolean[][] taken;
    Auditorium(String name, int rows, int seats) { this.name = name; taken = new boolean[rows][seats]; }
    Auditorium(Auditorium other) {
        this.name = other.name;
        this.taken = new boolean[other.taken.length][];
        for (int i = 0; i < taken.length; i++) taken[i] = other.taken[i].clone();
    }
    void book(int r, int s) { taken[r][s] = true; }
    @Override public Auditorium clone() {
        try {
            Auditorium copy = (Auditorium) super.clone();
            copy.taken = taken.clone();
            for (int i = 0; i < taken.length; i++) copy.taken[i] = taken[i].clone();
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
    @Override public String toString() {
        StringBuilder sb = new StringBuilder(name + ":");
        for (boolean[] r : taken) { sb.append(' '); for (boolean b : r) sb.append(b ? 'X' : '.'); }
        return sb.toString();
    }
}
class Movie implements Comparable<Movie> {
    final String title; final int duration;
    Movie(String t, int d) { title = t; duration = d; }
    @Override public int compareTo(Movie o) { return Integer.compare(duration, o.duration); }
    @Override public boolean equals(Object o) { return o instanceof Movie f && f.title.equals(title) && f.duration == duration; }
    @Override public int hashCode() { return Objects.hash(title, duration); }
}
