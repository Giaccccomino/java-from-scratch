import java.util.*;
import java.util.stream.*;

public class Stream1 {
    enum Genre { DRAMA, COMEDY, DOCUMENTARY, THRILLER }
    record Movie(String title, int year, int duration, double rating, Genre genre) {}

    public static void main(String[] args) {
        List<Movie> catalog = List.of(
            new Movie("A Night in the City", 2025, 112, 7.8, Genre.DRAMA),
            new Movie("The Big River", 2023, 95, 8.4, Genre.DOCUMENTARY),
            new Movie("Skyline", 2025, 128, 6.5, Genre.THRILLER),
            new Movie("Hilltop", 2023, 95, 6.9, Genre.DRAMA),
            new Movie("Éclair at Dawn", 2024, 101, 8.4, Genre.COMEDY),
            new Movie("Lighthouse", 2024, 134, 7.2, Genre.THRILLER),
            new Movie("Laughs in the Valley", 2025, 88, 7.5, Genre.COMEDY));

        List<String> longOnes = new ArrayList<>();
        for (Movie m : catalog) if (m.duration() > 100) longOnes.add(m.title().toUpperCase());
        Collections.sort(longOnes);
        System.out.println(longOnes);

        List<String> longOnes2 = catalog.stream()
            .filter(m -> m.duration() > 100)
            .map(m -> m.title().toUpperCase())
            .sorted()
            .toList();
        System.out.println(longOnes2);

        System.out.println(catalog.stream().filter(m -> m.year() == 2025).count());
        System.out.println(catalog.stream().mapToInt(Movie::duration).sum() + " " + catalog.stream().mapToInt(Movie::duration).average().getAsDouble());
        System.out.println(catalog.stream().max(Comparator.comparingDouble(Movie::rating)).get().title());
        System.out.println(catalog.stream().anyMatch(m -> m.rating() > 8) + " " + catalog.stream().allMatch(m -> m.duration() < 140) + " " + catalog.stream().noneMatch(m -> m.year() < 2020));
        System.out.println(catalog.stream().filter(m -> m.genre() == Genre.THRILLER).findFirst().map(Movie::title).orElse("none"));
        System.out.println(catalog.stream().map(Movie::title).sorted(Comparator.comparingInt(String::length)).limit(3).toList());
        System.out.println(catalog.stream().map(Movie::year).distinct().sorted().toList());
        System.out.println(catalog.stream().sorted(Comparator.comparingDouble(Movie::rating).reversed()).skip(1).limit(2).map(Movie::title).collect(Collectors.joining(", ", "[", "]")));
        IntSummaryStatistics st = catalog.stream().mapToInt(Movie::duration).summaryStatistics();
        System.out.println(st);

        Stream.of("a", "b", "c").forEach(System.out::print);
        System.out.println();
        System.out.println(IntStream.rangeClosed(1, 5).map(x -> x * x).boxed().toList());
        System.out.println(Stream.iterate(1, x -> x * 2).limit(10).toList());
        System.out.println(Stream.iterate(1, x -> x < 1000, x -> x * 3).toList());
        System.out.println(Arrays.stream(new int[]{5, 3, 8}).max().getAsInt() + " " + "skyline".chars().filter(ch -> ch != 'y').count());
        System.out.println(Stream.generate(() -> "x").limit(3).collect(Collectors.joining()));

        Stream<String> s = catalog.stream().map(Movie::title);
        s.count();
        try { s.count(); } catch (IllegalStateException e) { System.out.println(e.getMessage()); }

        Stream.of("Skyline", "Fog", "Hilltop")
            .filter(x -> { System.out.println("filter " + x); return x.length() > 3; })
            .map(x -> { System.out.println("map " + x); return x.toUpperCase(); })
            .findFirst();
        Stream<String> lazy = Stream.of("a").peek(x -> System.out.println("never printed without a terminal operation"));
        System.out.println("end");
    }
}
