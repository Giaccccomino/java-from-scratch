import java.util.*;
// Plain-Java checks of the code tested in Chapter 34 (the real tests use JUnit 5).
public class Check {
    static long ticketPrice(int age, boolean wednesday) {
        if (age < 0 || age > 120) throw new IllegalArgumentException("invalid age: " + age);
        if (wednesday) return 600;
        if (age < 3) return 0;
        if (age < 14 || age >= 65) return 700;
        return 950;
    }
    static class Cart {
        private final List<Long> prices = new ArrayList<>();
        void add(long p) { if (p < 0) throw new IllegalArgumentException("negative price: " + p); prices.add(p); }
        long total() { long t = prices.stream().mapToLong(Long::longValue).sum(); return prices.size() >= 10 ? t * 90 / 100 : t; }
        int count() { return prices.size(); }
    }
    public static void main(String[] a) {
        int[][] cases = {{2, 0}, {3, 700}, {13, 700}, {14, 950}, {64, 950}, {65, 700}, {120, 700}};
        for (int[] c : cases) System.out.print(c[0] + "->" + ticketPrice(c[0], false) + (ticketPrice(c[0], false) == c[1] ? " ok " : " KO "));
        System.out.println();
        Cart k = new Cart(); for (int i = 0; i < 10; i++) k.add(950);
        System.out.println(k.total() + " " + k.count());
        Cart k2 = new Cart(); for (int i = 0; i < 9; i++) k2.add(950);
        System.out.println(k2.total());
    }
}
