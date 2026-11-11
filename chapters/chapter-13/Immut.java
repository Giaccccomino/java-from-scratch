public class Immut {
    public static void main(String[] args) {
        Movie m = new Movie("A Night in the City", 112);
        Movie r = m.withTitle("A Night in the City (restored)");
        System.out.println(m + " | " + r);
        String s = "skyline";
        s.toUpperCase();
        System.out.println(s);
    }
}
final class Movie {
    private final String title;
    private final int durationMinutes;
    Movie(String title, int durationMinutes) { this.title = title; this.durationMinutes = durationMinutes; }
    String getTitle() { return title; }
    int getDurationMinutes() { return durationMinutes; }
    Movie withTitle(String newTitle) { return new Movie(newTitle, durationMinutes); }
    @Override public String toString() { return title + " (" + durationMinutes + " min)"; }
}
