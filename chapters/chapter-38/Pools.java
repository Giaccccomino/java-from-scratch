import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class Pools {
    static long screenTakings(int screen) throws InterruptedException {
        Thread.sleep(200);                       // simulates a slow database read
        return 1000L * screen + 75;
    }

    public static void main(String[] args) throws Exception {
        // 1. ExecutorService and Future
        long t0 = System.nanoTime();
        try (ExecutorService pool = Executors.newFixedThreadPool(4)) {
            List<Future<Long>> results = new ArrayList<>();
            for (int screen = 1; screen <= 8; screen++) {
                int s = screen;
                results.add(pool.submit(() -> screenTakings(s)));
            }
            long total = 0;
            for (Future<Long> f : results) total += f.get();
            System.out.printf("Total takings: %d cents in %d ms%n", total, (System.nanoTime() - t0) / 1_000_000);
        }

        // 2. AtomicInteger
        AtomicInteger sold = new AtomicInteger();
        try (ExecutorService pool = Executors.newFixedThreadPool(2)) {
            for (int c = 0; c < 2; c++) pool.submit(() -> { for (int i = 0; i < 100_000; i++) sold.incrementAndGet(); });
        }
        System.out.println("Tickets sold: " + sold.get());

        // 3. ConcurrentHashMap.merge
        Map<String, Integer> byMovie = new ConcurrentHashMap<>();
        String[] movies = {"The Deep Blue", "Cinema Paradise", "The Deep Blue"};
        try (ExecutorService pool = Executors.newFixedThreadPool(3)) {
            for (int c = 0; c < 3; c++) pool.submit(() -> {
                for (int i = 0; i < 10_000; i++) byMovie.merge(movies[i % 3], 1, Integer::sum);
            });
        }
        System.out.println(new TreeMap<>(byMovie));

        // 4. an exception inside a task
        try (ExecutorService pool = Executors.newSingleThreadExecutor()) {
            Future<Integer> f = pool.submit(() -> Integer.parseInt("12a"));
            try { f.get(); }
            catch (ExecutionException e) { System.out.println("The task failed: " + e.getCause()); }
        }

        // 5. timeout
        try (ExecutorService pool = Executors.newSingleThreadExecutor()) {
            Future<Long> slow = pool.submit(() -> { Thread.sleep(5000); return 0L; });
            try { slow.get(300, TimeUnit.MILLISECONDS); }
            catch (TimeoutException e) { System.out.println("Bank too slow, cancelling"); slow.cancel(true); }
        }

        // 6. CountDownLatch
        CountDownLatch ready = new CountDownLatch(3);
        for (int c = 1; c <= 3; c++) {
            int n = c;
            Thread.ofPlatform().start(() -> { System.out.println("register " + n + " open"); ready.countDown(); });
        }
        ready.await();
        System.out.println("All registers open: the doors open");
    }
}
