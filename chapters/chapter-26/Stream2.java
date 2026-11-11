import java.util.*;
import java.util.stream.*;

public class Stream2 {
    enum Genre { DRAMA, COMEDY, DOCUMENTARY, THRILLER }
    record Movie(String title, int year, int duration, double rating, Genre genre) {}
    record Sale(String movie, String day, int tickets, long takings) {}
    record Show(String screen, List<String> times) {}

    public static void main(String[] args) {
        List<Movie> catalog = List.of(
            new Movie("A Night in the City", 2025, 112, 7.8, Genre.DRAMA),
            new Movie("The Big River", 2023, 95, 8.4, Genre.DOCUMENTARY),
            new Movie("Skyline", 2025, 128, 6.5, Genre.THRILLER),
            new Movie("Hilltop", 2023, 95, 6.9, Genre.DRAMA),
            new Movie("Éclair at Dawn", 2024, 101, 8.4, Genre.COMEDY),
            new Movie("Lighthouse", 2024, 134, 7.2, Genre.THRILLER),
            new Movie("Laughs in the Valley", 2025, 88, 7.5, Genre.COMEDY));

        Map<Genre, List<String>> byGenre = catalog.stream()
            .collect(Collectors.groupingBy(Movie::genre, TreeMap::new, Collectors.mapping(Movie::title, Collectors.toList())));
        System.out.println(byGenre);
        Map<Integer, Long> byYear = catalog.stream().collect(Collectors.groupingBy(Movie::year, TreeMap::new, Collectors.counting()));
        System.out.println(byYear);
        Map<Genre, Double> avgRating = catalog.stream().collect(Collectors.groupingBy(Movie::genre, () -> new EnumMap<>(Genre.class), Collectors.averagingDouble(Movie::rating)));
        System.out.println(avgRating);
        Map<Boolean, List<String>> parts = catalog.stream().collect(Collectors.partitioningBy(m -> m.duration() > 100, Collectors.mapping(Movie::title, Collectors.toList())));
        System.out.println(parts);
        Map<String, Integer> durations = catalog.stream().collect(Collectors.toMap(Movie::title, Movie::duration));
        System.out.println(durations.get("Skyline"));
        try {
            catalog.stream().collect(Collectors.toMap(Movie::year, Movie::title));
        } catch (IllegalStateException e) { System.out.println(e.getMessage()); }
        Map<Integer, String> yearTitles = catalog.stream().collect(Collectors.toMap(Movie::year, Movie::title, (a, b) -> a + " | " + b, TreeMap::new));
        System.out.println(yearTitles);
        Map<Genre, Optional<Movie>> best = catalog.stream().collect(Collectors.groupingBy(Movie::genre, Collectors.maxBy(Comparator.comparingDouble(Movie::rating))));
        System.out.println(best.get(Genre.THRILLER).get().title());
        System.out.println(catalog.stream().map(Movie::title).collect(Collectors.joining(" · ")));

        List<Sale> sales = List.of(
            new Sale("Skyline", "sat", 120, 114000), new Sale("River", "sat", 80, 76000),
            new Sale("Skyline", "sun", 95, 90250), new Sale("Hilltop", "sun", 40, 38000), new Sale("River", "mon", 30, 18000));
        Map<String, Integer> ticketsPerMovie = sales.stream().collect(Collectors.groupingBy(Sale::movie, TreeMap::new, Collectors.summingInt(Sale::tickets)));
        System.out.println(ticketsPerMovie);
        long total = sales.stream().mapToLong(Sale::takings).sum();
        System.out.printf("Total takings: $%.2f%n", total / 100.0);
        System.out.println(sales.stream().map(Sale::takings).reduce(0L, Long::sum) + " " + sales.stream().map(Sale::tickets).reduce(Integer::max).get());

        List<Show> sched = List.of(new Show("Screen 1", List.of("18:00", "20:30")), new Show("Screen 2", List.of("17:30", "20:30", "22:45")));
        System.out.println(sched.stream().flatMap(sh -> sh.times().stream()).distinct().sorted().toList());
        System.out.println(Stream.of("the big river", "skyline").flatMap(t -> Arrays.stream(t.split(" "))).toList());
        System.out.println(catalog.stream().map(Movie::title).mapMulti((String t, java.util.function.Consumer<Object> out) -> { if (t.contains(" ")) out.accept(t.length()); }).toList());

        List<Integer> mod = catalog.stream().map(Movie::duration).toList();
        try { mod.add(1); } catch (UnsupportedOperationException e) { System.out.println("toList() is immutable"); }
        List<Integer> mod2 = catalog.stream().map(Movie::duration).collect(Collectors.toCollection(ArrayList::new));
        mod2.add(1);
        long t0 = System.nanoTime();
        long a = LongStream.rangeClosed(1, 200_000_000).filter(x -> x % 7 == 0).sum();
        long t1 = System.nanoTime();
        long b = LongStream.rangeClosed(1, 200_000_000).parallel().filter(x -> x % 7 == 0).sum();
        long t2 = System.nanoTime();
        System.out.println((a == b) + " seq " + (t1 - t0) / 1_000_000 + " ms, par " + (t2 - t1) / 1_000_000 + " ms, cores " + Runtime.getRuntime().availableProcessors());
        List<Integer> wrong = new ArrayList<>();
        IntStream.range(0, 10_000).parallel().forEach(wrong::add);
        System.out.println("with parallel and ArrayList: " + wrong.size() + " elements (10000 expected)");
        System.out.println(Stream.of(5, 3, 8, 1).takeWhile(x -> x > 2).toList() + " " + Stream.of(5, 3, 8, 1).dropWhile(x -> x > 2).toList());
    }
}
