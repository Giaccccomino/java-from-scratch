import java.time.*;
import java.util.*;
import java.util.function.*;

public class Patterns {
    // STRATEGY
    interface Discount { long apply(long price); }
    static final Discount NONE = p -> p;
    static final Discount STUDENTS = p -> p * 80 / 100;
    static Discount flat(long cents) { return p -> Math.max(0, p - cents); }

    static Discount discountFor(String code) {
        return switch (code) {
            case "STUD" -> STUDENTS;
            case "VOUCHER3" -> flat(300);
            case "" -> NONE;
            default -> throw new IllegalArgumentException("unknown discount code: " + code);
        };
    }

    // BUILDER
    static final class Showing {
        private final String movie, screen; private final LocalDateTime start;
        private final boolean threeD, subtitles; private final int duration;
        private Showing(Builder b) {
            movie = b.movie; screen = b.screen; start = b.start; threeD = b.threeD; subtitles = b.subtitles; duration = b.duration;
        }
        static Builder builder(String movie, String screen) { return new Builder(movie, screen); }
        public String toString() {
            return movie + " – " + screen + " – " + start + (threeD ? " – 3D" : "") + (subtitles ? " – subtitled" : "") + " – " + duration + " min";
        }
        static final class Builder {
            private final String movie, screen; private LocalDateTime start; private boolean threeD, subtitles; private int duration = 120;
            private Builder(String movie, String screen) { this.movie = movie; this.screen = screen; }
            Builder start(LocalDateTime t) { start = t; return this; }
            Builder threeD() { threeD = true; return this; }
            Builder subtitles() { subtitles = true; return this; }
            Builder duration(int minutes) { duration = minutes; return this; }
            Showing build() {
                if (start == null) throw new IllegalStateException("start is required");
                return new Showing(this);
            }
        }
    }

    // OBSERVER
    static class Screen {
        private int free;
        private final List<Consumer<Integer>> observers = new ArrayList<>();
        Screen(int capacity) { free = capacity; }
        void onChange(Consumer<Integer> observer) { observers.add(observer); }
        void book(int n) {
            free -= n;
            observers.forEach(o -> o.accept(free));
        }
    }

    // DECORATOR
    interface Price { long cents(); String description(); }
    record Base(long cents) implements Price { public String description() { return "ticket"; } }
    record ThreeDSurcharge(Price inner) implements Price {
        public long cents() { return inner.cents() + 250; }
        public String description() { return inner.description() + " + 3D"; }
    }
    record PremiumSeat(Price inner) implements Price {
        public long cents() { return inner.cents() + 300; }
        public String description() { return inner.description() + " + premium seat"; }
    }

    // TEMPLATE METHOD
    static abstract class Report {
        final String generate() { return header() + "\n" + body() + "\n" + footer(); }
        String header() { return "== Aurora Cinema =="; }
        abstract String body();
        String footer() { return "== end =="; }
    }
    static class TakingsReport extends Report { String body() { return "Today's takings: $4,275.00"; } }

    // SINGLETON
    enum Configuration {
        INSTANCE;
        private final Map<String, String> values = new HashMap<>(Map.of("currency", "USD"));
        String get(String key) { return values.get(key); }
    }

    public static void main(String[] args) {
        for (Discount d : List.of(NONE, STUDENTS, flat(300))) System.out.println(d.apply(950));
        System.out.println(discountFor("STUD").apply(950) + " " + discountFor("").apply(950));
        System.out.println(Showing.builder("The Deep Blue", "Screen 2")
            .start(LocalDateTime.of(2026, 10, 30, 21, 15)).threeD().duration(168).build());
        try { Showing.builder("X", "Screen 1").build(); } catch (IllegalStateException e) { System.out.println(e.getMessage()); }
        Screen s = new Screen(12);
        s.onChange(f -> System.out.println("display: " + f + " seats free"));
        s.onChange(f -> { if (f < 10) System.out.println("alert: Screen 3 almost full"); });
        s.book(2); s.book(3);
        Price p = new PremiumSeat(new ThreeDSurcharge(new Base(950)));
        System.out.println(p.description() + ": " + p.cents());
        System.out.println(new TakingsReport().generate());
        System.out.println(Configuration.INSTANCE.get("currency"));
    }
}
