public class WarmUp {
    static int price(int age) {
        if (age < 14 || age >= 65) return 700;
        return 950;
    }
    public static void main(String[] a) {
        for (int round = 1; round <= 6; round++) {
            long t = System.nanoTime();
            long total = 0;
            for (int i = 0; i < 1_000_000; i++) total += price(i % 90);
            System.out.printf("round %d: %5d µs (total %d)%n", round, (System.nanoTime() - t) / 1000, total);
        }
    }
}
