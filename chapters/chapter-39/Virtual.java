import java.time.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class Virtual {
    static void request() {
        try { Thread.sleep(Duration.ofSeconds(1)); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    static long trial(ExecutorService ex, int n) {
        long t0 = System.nanoTime();
        try (ex) {
            IntStream.range(0, n).forEach(i -> ex.submit(Virtual::request));
        }
        return (System.nanoTime() - t0) / 1_000_000;
    }

    public static void main(String[] args) throws Exception {
        Thread v = Thread.ofVirtual().name("customer-1").start(() -> System.out.println("inside: " + Thread.currentThread()));
        v.join();
        System.out.println("isVirtual: " + v.isVirtual());
        System.out.println("200 platform threads, 10,000 requests: " + trial(Executors.newFixedThreadPool(200), 10_000) + " ms");
        System.out.println("virtual threads, 10,000 requests: " + trial(Executors.newVirtualThreadPerTaskExecutor(), 10_000) + " ms");
        System.out.println("virtual threads, 100,000 requests: " + trial(Executors.newVirtualThreadPerTaskExecutor(), 100_000) + " ms");
    }
}
