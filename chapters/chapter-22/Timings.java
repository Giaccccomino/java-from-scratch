import java.util.*;
public class Timings {
    static long ms(Runnable r) { long t = System.nanoTime(); r.run(); return (System.nanoTime() - t) / 1_000_000; }
    public static void main(String[] args) {
        for (int round = 0; round < 2; round++) {
            List<Integer> a = new ArrayList<>(), l = new LinkedList<>();
            long t1 = ms(() -> { for (int i = 0; i < 100_000; i++) a.add(0, i); });
            long t2 = ms(() -> { for (int i = 0; i < 100_000; i++) l.add(0, i); });
            long t3 = ms(() -> { long s = 0; for (int i = 0; i < 100_000; i += 10) s += a.get(i); });
            long t4 = ms(() -> { long s = 0; for (int i = 0; i < 100_000; i += 10) s += l.get(i); });
            long t5 = ms(() -> { for (int i = 0; i < 1_000_000; i++) a.add(i); });
            long t6 = ms(() -> { for (int i = 0; i < 1_000_000; i++) l.add(i); });
            if (round == 1) System.out.printf("add(0): AL %d ms, LL %d ms | get(i) x10000: AL %d ms, LL %d ms | add at end x1M: AL %d ms, LL %d ms%n", t1, t2, t3, t4, t5, t6);
        }
    }
}
