import java.util.*;
import java.util.function.*;

public class Lambdas {
    record Movie(String title, int duration, double rating) {}
    static String upperTitle(Movie m) { return m.title().toUpperCase(); }

    public static void main(String[] args) {
        Predicate<Movie> isLong = m -> m.duration() > 110;
        Function<Movie, String> title = m -> m.title();
        Consumer<String> print = s -> System.out.println("> " + s);
        Supplier<List<Movie>> newList = ArrayList::new;
        UnaryOperator<String> shout = s -> s.toUpperCase() + "!";
        BinaryOperator<Integer> sum = (a, b) -> a + b;
        BiFunction<Integer, Double, Long> total = (n, p) -> Math.round(n * p * 100);
        IntPredicate even = n -> n % 2 == 0;
        ToIntFunction<Movie> duration = Movie::duration;
        IntBinaryOperator max = Math::max;

        Movie m = new Movie("A Night in the City", 112, 7.8);
        System.out.println(isLong.test(m) + " " + title.apply(m) + " " + shout.apply("skyline") + " " + sum.apply(2, 3) + " " + total.apply(3, 9.5) + " " + even.test(7) + " " + duration.applyAsInt(m) + " " + max.applyAsInt(4, 9));
        print.accept("hello");
        List<Movie> l = newList.get(); l.add(m); System.out.println(l.size());

        Predicate<Movie> isGood = x -> x.rating() >= 8;
        Predicate<Movie> isShort = isLong.negate();
        Movie river = new Movie("The Big River", 95, 8.4);
        System.out.println(isLong.and(isGood).test(m) + " " + isLong.or(isGood).test(river) + " " + isShort.test(river) + " " + Predicate.not(isLong).test(m));
        Function<Movie, String> t = Movie::title;
        Function<Movie, Integer> length = t.andThen(String::length);
        System.out.println(length.apply(river) + " " + t.andThen(shout).apply(river) + " " + shout.compose((String s) -> s.trim()).apply("  river  "));
        Consumer<String> log = s -> System.out.print("[log] " + s + " ");
        log.andThen(print).accept("event");

        Function<Movie, String> m1 = Lambdas::upperTitle;
        Function<String, Integer> m2 = Integer::parseInt;
        Function<Movie, String> m3 = Movie::title;
        Supplier<String> m4 = m::title;
        Function<String, StringBuilder> m5 = StringBuilder::new;
        BiFunction<String, String, Boolean> m6 = String::equalsIgnoreCase;
        System.out.println(m1.apply(river) + " " + (m2.apply("41") + 1) + " " + m3.apply(river) + " " + m4.get() + " " + m5.apply("ab").reverse() + " " + m6.apply("SKYLINE", "skyline"));
        IntFunction<int[]> arr = int[]::new;
        System.out.println(arr.apply(5).length);

        Runnable r = () -> System.out.println("done");
        r.run();
        Comparator<String> c = (String a, String b) -> { int x = a.length() - b.length(); return x != 0 ? x : a.compareTo(b); };
        System.out.println(c.compare("fog", "skyline") < 0);
        var v = (Predicate<String>) s -> s.isEmpty();
        System.out.println(v.test(""));
    }
}
