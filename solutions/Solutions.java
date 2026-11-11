import java.math.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

// The complete solutions of Appendix D, in a single program: java Solutions.java
public class Solutions {
    // 8.10 Easter (Meeus/Jones/Butcher)
    static int[] easter(int year) {
        int a = year % 19, b = year / 100, c = year % 100, d = b / 4, e = b % 4;
        int f = (b + 8) / 25, g = (b - f + 1) / 3, h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4, k = c % 4, l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int month = (h + l - 7 * m + 114) / 31, day = (h + l - 7 * m + 114) % 31 + 1;
        return new int[]{day, month};
    }

    // 9.10 Collatz
    static int steps(long n) {
        int s = 0;
        while (n != 1) { n = (n % 2 == 0) ? n / 2 : 3 * n + 1; s++; }
        return s;
    }

    // 10.9 seats together
    static int[] seatsTogether(boolean[][] taken, int k) {
        for (int r = taken.length - 1; r >= 0; r--) {               // from the back
            int run = 0;
            for (int s = 0; s < taken[r].length; s++) {
                run = taken[r][s] ? 0 : run + 1;
                if (run == k) return new int[]{r, s - k + 1};
            }
        }
        return null;
    }

    // 11.4
    static int increase(int x) { x++; return x; }
    static void reset(int[] v) { v[0] = 0; v = new int[]{7}; }

    // 11.9 Fibonacci with memory
    static long calls = 0;
    static long fib(int n, long[] memo) {
        calls++;
        if (n <= 1) return n;
        if (memo[n] != 0) return memo[n];
        return memo[n] = fib(n - 1, memo) + fib(n - 2, memo);
    }

    // 12.3
    static class Screen {
        final String name; final int capacity; int sold;
        Screen(String name, int capacity) { this.name = name; this.capacity = capacity; }
        boolean book(int seats) { if (seats <= 0 || seats > capacity - sold) return false; sold += seats; return true; }
        @Override public String toString() { return name + " [" + sold + "/" + capacity + "]"; }
    }

    // 13.3
    static class Counter { static int a = 0; int b = 0; Counter() { a++; b++; } }

    // 14.2
    static class X { X() { System.out.print("X"); } void m() { System.out.print("x"); } }
    static class Y extends X { Y() { System.out.print("Y"); } @Override void m() { System.out.print("y"); super.m(); } }
    static class Z extends Y { Z() { super(); System.out.print("Z"); } }

    // 18.3
    static class Outer { int x = 1; class Inner { int x = 2; void print(int x) { System.out.println(x + " " + this.x + " " + Outer.this.x); } } }

    // 20.2
    static String test(int x) {
        try { if (x == 0) throw new IllegalStateException("zero"); return "ok " + (10 / x); }
        catch (IllegalStateException e) { return "catch " + e.getMessage(); }
        finally { System.out.print("[finally " + x + "] "); }
    }

    // 23.4 a stack of brackets
    static boolean balanced(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '(', '[', '{' -> stack.push(c);
                case ')', ']', '}' -> {
                    if (stack.isEmpty()) return false;
                    char o = stack.pop();
                    if ((c == ')' && o != '(') || (c == ']' && o != '[') || (c == '}' && o != '{')) return false;
                }
                default -> {}
            }
        }
        return stack.isEmpty();
    }

    // 27.4 safe parsing
    static OptionalInt parseInt(String s) {
        try { return OptionalInt.of(Integer.parseInt(s.strip())); }
        catch (NumberFormatException e) { return OptionalInt.empty(); }
    }

    // 28.8 splitting the takings
    static List<BigDecimal> split(BigDecimal total, int parts) {
        BigDecimal base = total.divide(BigDecimal.valueOf(parts), 2, RoundingMode.DOWN);
        BigDecimal rest = total.subtract(base.multiply(BigDecimal.valueOf(parts)));
        int cents = rest.movePointRight(2).intValueExact();
        List<BigDecimal> r = new ArrayList<>();
        for (int i = 0; i < parts; i++) r.add(i < cents ? base.add(new BigDecimal("0.01")) : base);
        return r;
    }

    record P(int x, int y) {}
    record Q(String name) {}

    public static void main(String[] args) throws Exception {
        // 2 challenge
        System.out.println("2: " + (2 * 60 + 17) + " min, " + (120 - 87) + " seats, $" + 87 * 9.50 + ", " + 7 / 2 + " " + 7.0 / 2);
        // 4 challenge
        double inDollars = 2_060_000 * 9.50 + 940_000 * 7.0;
        int inInt = 2_060_000 * 950 + 940_000 * 700;
        long inLong = 2_060_000L * 950 + 940_000L * 700;
        System.out.println("4: " + inDollars + " | " + inInt + " | " + inLong);
        // 5 basic
        int minutes = 517;
        System.out.println("5: " + minutes / 60 + " h " + minutes % 60 + " min");
        int start = 14 * 60 + 30, end = start + minutes;
        System.out.printf("5: ends at %02d:%02d%n", end / 60 % 24, end % 60);
        // 6 intermediate
        String code = "AUR-2026-S2-F12";
        String[] parts = code.split("-");
        System.out.println("6: Screen " + parts[2].substring(1) + ", row " + parts[3].charAt(0) + ", seat " + parts[3].substring(1));
        // 8.10
        for (int year : new int[]{2025, 2026, 2027}) { int[] e = easter(year); System.out.println("8.10: " + year + " -> " + e[1] + "/" + e[0]); }
        // 9.10
        long t0 = System.nanoTime(); int best = 1, max = 0;
        for (int n = 1; n <= 1_000_000; n++) { int s = steps(n); if (s > max) { max = s; best = n; } }
        System.out.println("9.10: " + best + " with " + max + " steps (" + (System.nanoTime() - t0) / 1_000_000 + " ms)");
        // 10.9
        boolean[][] taken = new boolean[5][10];
        taken[4][1] = true; taken[4][6] = true; taken[3][4] = true;
        for (int k : new int[]{1, 4, 5, 10, 11}) System.out.println("10.9: k=" + k + " -> " + Arrays.toString(seatsTogether(taken, k)));
        // 11.4
        int a = 5; int b = increase(a); int[] c = {1, 2}; reset(c);
        System.out.println("11.4: " + a + " " + b + " " + c[0] + " " + c.length);
        // 11.9
        System.out.println("11.9: fib(40) = " + fib(40, new long[41]) + " with " + calls + " calls");
        // 12.3
        Screen x = new Screen("A", 10), y = x, z = new Screen("A", 10);
        y.book(4); z.book(2);
        System.out.println("12.3: " + x + " " + y + " " + z + " " + (x == y) + " " + (x == z));
        // 13.3
        new Counter(); new Counter(); Counter cc = new Counter();
        System.out.println("13.3: " + Counter.a + " " + cc.b);
        // 14.2
        System.out.print("14.2: "); X o = new Z(); o.m(); System.out.println();
        // 16.4 discounts as lambdas
        UnaryOperator<Long> over65 = p -> p - 250, group = p -> p * 90 / 100, roundDown = p -> p / 10 * 10;
        for (long price : new long[]{950, 1350})
            System.out.println("16.4: " + price + " -> " + over65.andThen(group).andThen(roundDown).apply(price) + " / " + group.andThen(over65).andThen(roundDown).apply(price));
        // 17.3
        P p1 = new P(1, 2), p2 = new P(1, 2);
        System.out.println("17.3: " + (p1 == p2) + " " + p1.equals(p2) + " " + p1 + " " + new P(2, 1).equals(p1));
        // 18.3
        System.out.print("18.3: "); new Outer().new Inner().print(3);
        // 19.3 and 19.4
        System.out.println("19.3: " + "Aa".hashCode() + " " + "BB".hashCode());
        List<Q> qs = new ArrayList<>(List.of(new Q("a"), new Q("b"), new Q("a")));
        System.out.println("19.4: " + qs.indexOf(new Q("a")) + " " + qs.lastIndexOf(new Q("a")) + " " + new HashSet<>(qs).size());
        // 20.2
        System.out.println(test(2)); System.out.println(test(0)); System.out.println(test(-5));
        // 21.2
        List<Integer> l = new ArrayList<>(List.of(5, 1, 3, 1));
        l.remove(1); l.remove(Integer.valueOf(1));
        Integer ia = 1000, ib = 1000;
        System.out.println("21.2: " + l + " " + (ia == ib) + " " + ia.equals(ib) + " " + (ia <= ib && ia >= ib));
        // 22.2
        List<String> s = new ArrayList<>(List.of("a", "b", "c", "d"));
        s.add(2, "x"); s.remove("a"); s.set(0, s.get(s.size() - 1));
        System.out.println("22.2: " + s + " " + s.indexOf("d") + " " + s.subList(1, 3));
        // 23.3 and 23.4
        Map<String, Integer> m = new TreeMap<>();
        for (String w : "the river the harbor the river".split(" ")) m.merge(w, 1, Integer::sum);
        System.out.println("23.3: " + m + " " + m.getOrDefault("skyline", 0) + " " + m.keySet().iterator().next());
        System.out.println("23.4: " + balanced("{[()()]}") + " " + balanced("([)]") + " " + balanced("(("));
        // 24.2
        List<String> t = new ArrayList<>(List.of("River", "harbor", "Skyline", "aurora", "Ape"));
        t.sort(null); System.out.print("24.2: " + t + " ");
        t.sort(String.CASE_INSENSITIVE_ORDER.reversed()); System.out.print(t + " ");
        t.sort(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())); System.out.println(t);
        // 25.3
        Function<Integer, Integer> dbl = v -> v * 2, plus3 = v -> v + 3;
        System.out.println("25.3: " + dbl.andThen(plus3).apply(5) + " " + dbl.compose(plus3).apply(5));
        Predicate<String> empty = String::isEmpty;
        System.out.println("25.3: " + empty.negate().or(v -> v == null).test(""));
        // 26.3
        System.out.println("26.3: " + Stream.of("river", "skyline", "fog", "river", "harbor").distinct().filter(v -> v.length() > 3).map(String::length).reduce(0, Integer::sum)
                + " " + IntStream.range(0, 5).skip(1).limit(3).sum());
        // 27.2 and 27.4
        Optional<String> oa = Optional.of("  skyline  "), ob = Optional.empty();
        System.out.println("27.2: " + oa.map(String::strip).filter(v -> v.length() > 3).orElse("short") + " "
                + ob.map(String::strip).orElse("empty") + " " + oa.flatMap(v -> ob).isPresent() + " " + ob.or(() -> oa).get().length());
        System.out.println("27.4: " + Stream.of("12", "x", "7", "", "3").map(Solutions::parseInt).filter(OptionalInt::isPresent).mapToInt(OptionalInt::getAsInt).sum());
        // 28.2, 28.3, 28.5, 28.7, 28.8
        System.out.println("28.2: " + LocalDateTime.of(2026, 10, 31, 22, 45).plusMinutes(128 + 15));
        LocalDate d = LocalDate.of(2026, 3, 31); d.plusMonths(1);
        System.out.println("28.3: " + d + " " + d.plusMonths(1) + " " + d.minusMonths(1) + " " + d.getDayOfWeek());
        System.out.println("28.3: " + new BigDecimal("10").divide(new BigDecimal("4")) + " " + new BigDecimal("2.50").add(new BigDecimal("0.5")));
        System.out.println("28.5: " + LocalDate.of(2026, 8, 31).plusMonths(6));
        ZoneId ny = ZoneId.of("America/New_York");
        LocalDateTime midnight = LocalDateTime.of(2026, 11, 1, 0, 0);
        System.out.println("28.7: " + midnight.plusMinutes(150) + " | " + midnight.atZone(ny).plusMinutes(150) + " | " + ny.getRules().nextTransition(Instant.parse("2026-01-01T00:00:00Z")));
        System.out.println("28.8: " + split(new BigDecimal("100.00"), 3) + " " + split(new BigDecimal("10.00"), 7) + " " + split(new BigDecimal("0.05"), 10));
        // 29.3
        System.out.println("29.3: " + "River;Harbor;;Bay;;".split(";").length + " " + "River;Harbor;;Bay;;".split(";", -1).length);
        System.out.println("29.3: " + "a1b22c333".replaceAll("\\d+", "#") + " " + "a1b22c333".replaceAll("\\d", "#"));
        System.out.println("29.3: " + new StringBuilder("radar").reverse().toString().equals("radar"));
        // 30.3
        Path p = Files.createTempDirectory("ex").resolve("test.txt");
        Files.writeString(p, "one\n"); Files.writeString(p, "two\n", StandardOpenOption.APPEND); Files.writeString(p, "three\n");
        System.out.println("30.3: " + Files.readAllLines(p) + " " + Files.size(p));
        // 31.2
        System.out.print("31.2: ");
        Object[] values = {42, -3, "", "River", 2.5, null};
        for (Object v : values) {
            String r = switch (v) {
                case null -> "N";
                case Integer i when i < 0 -> "neg";
                case Integer i -> "int";
                case String str when str.isEmpty() -> "empty";
                case String str -> str.toUpperCase();
                default -> "?";
            };
            System.out.print(r + " ");
        }
        System.out.println();
    }
}
