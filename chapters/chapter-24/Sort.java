import java.text.Collator;
import java.util.*;

public class Sort {
    record Movie(String title, int year, int duration, Double rating) {}

    public static void main(String[] args) {
        List<Movie> movies = new ArrayList<>(List.of(
            new Movie("A Night in the City", 2025, 112, 7.8),
            new Movie("The Big River", 2023, 95, 8.4),
            new Movie("Skyline", 2025, 128, null),
            new Movie("Hilltop", 2023, 95, 6.9),
            new Movie("Éclair at Dawn", 2024, 101, 8.4)
        ));
        Comparator<Movie> byDuration = new Comparator<>() {
            @Override public int compare(Movie a, Movie b) { return Integer.compare(a.duration(), b.duration()); }
        };
        Comparator<Movie> byDuration2 = (a, b) -> Integer.compare(a.duration(), b.duration());
        Comparator<Movie> byDuration3 = Comparator.comparingInt(Movie::duration);
        movies.sort(byDuration3);
        print("by duration", movies);
        movies.sort(Comparator.comparingInt(Movie::duration).reversed());
        print("descending duration", movies);
        movies.sort(Comparator.comparingInt(Movie::year).reversed().thenComparing(Movie::title));
        print("descending year, then title", movies);
        movies.sort(Comparator.comparing(Movie::rating, Comparator.nullsLast(Comparator.reverseOrder())).thenComparingInt(Movie::duration));
        print("descending rating (null last), then duration", movies);
        try { movies.sort(Comparator.comparing(Movie::rating)); } catch (NullPointerException e) { System.out.println("NPE: null rating without nullsLast"); }
        movies.sort(Comparator.comparing(Movie::title));
        print("title (String.compareTo)", movies);
        movies.sort(Comparator.comparing(Movie::title, String.CASE_INSENSITIVE_ORDER));
        print("title ignoring case", movies);
        Collator en = Collator.getInstance(Locale.ENGLISH);
        movies.sort(Comparator.comparing(Movie::title, en));
        print("title with an English Collator", movies);
        List<String> words = new ArrayList<>(List.of("résumé", "resume", "Rome", "rose", "Paris", "pear", "zebra", "Zurich", "éclair", "apple"));
        words.sort(null); System.out.println(words);
        words.sort(en); System.out.println(words);
        Collator primary = Collator.getInstance(Locale.ENGLISH);
        primary.setStrength(Collator.PRIMARY);
        System.out.println(primary.compare("resume", "Résumé") + " " + en.compare("resume", "résumé"));
        System.out.println(Comparator.<Integer>naturalOrder().compare(3, 5) + " " + Comparator.<Integer>reverseOrder().compare(3, 5));
        Movie max = Collections.max(movies, Comparator.comparingInt(Movie::duration));
        System.out.println("longest: " + max.title());
        System.out.println(Integer.compare(Integer.MIN_VALUE, 1) + " " + (Integer.MIN_VALUE - 1));
        List<String> ss = new ArrayList<>(List.of("bb", "a", "ccc", "dd", "e"));
        ss.sort(Comparator.comparingInt(String::length));
        System.out.println(ss);
    }

    static void print(String t, List<Movie> l) {
        System.out.print(t + ": ");
        for (Movie m : l) System.out.print(m.title() + "(" + m.year() + "," + m.duration() + "," + m.rating() + ") ");
        System.out.println();
    }
}
