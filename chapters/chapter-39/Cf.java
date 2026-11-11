import java.util.*;
import java.util.concurrent.*;

public class Cf {
    static void await(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { throw new CompletionException(e); }
    }
    static long t0 = System.nanoTime();
    static String t() { return String.format("[%4d ms %s]", (System.nanoTime() - t0) / 1_000_000, Thread.currentThread().getName()); }

    record Customer(String id, String name) {}
    static Customer findCustomer(String id) { await(300); System.out.println(t() + " customer found"); return new Customer(id, "Julia"); }
    static int freeSeats(String screen) { await(400); System.out.println(t() + " seats read"); return 37; }
    static CompletableFuture<String> payAsync(Customer c, long amount) {
        return CompletableFuture.supplyAsync(() -> { await(200); return "PAY-" + c.id() + "-" + amount; });
    }

    public static void main(String[] args) throws Exception {
        // 1. combining two independent operations
        CompletableFuture<Customer> customer = CompletableFuture.supplyAsync(() -> findCustomer("4821"));
        CompletableFuture<Integer> seats = CompletableFuture.supplyAsync(() -> freeSeats("Screen 3"));
        String result = customer
                .thenCombine(seats, (c, s) -> c.name() + ", free seats: " + s)
                .join();
        System.out.println(t() + " " + result);

        // 2. a dependent chain with thenCompose
        t0 = System.nanoTime();
        String receipt = CompletableFuture.supplyAsync(() -> findCustomer("4821"))
                .thenCompose(c -> payAsync(c, 2850))
                .thenApply(code -> "receipt " + code)
                .join();
        System.out.println(t() + " " + receipt);

        // 3. exceptions
        CompletableFuture<Integer> broken = CompletableFuture.supplyAsync(() -> Integer.parseInt("12a"));
        System.out.println(broken.exceptionally(e -> { System.out.println("error: " + e); return -1; }).join());
        try { broken.join(); } catch (CompletionException e) { System.out.println("join: " + e.getCause().getClass().getSimpleName()); }
        try { broken.get(); } catch (ExecutionException e) { System.out.println("get: " + e.getCause().getClass().getSimpleName()); }
        String h = broken.handle((value, error) -> error == null ? "ok " + value : "failed").join();
        System.out.println(h);

        // 4. timeouts
        String bank = CompletableFuture.supplyAsync(() -> { await(3000); return "OK"; })
                .completeOnTimeout("BANK NOT RESPONDING", 500, TimeUnit.MILLISECONDS)
                .join();
        System.out.println(bank);
        try {
            CompletableFuture.supplyAsync(() -> { await(3000); return "OK"; }).orTimeout(200, TimeUnit.MILLISECONDS).join();
        } catch (CompletionException e) { System.out.println("orTimeout: " + e.getCause()); }

        // 5. allOf
        t0 = System.nanoTime();
        List<CompletableFuture<Integer>> screens = new ArrayList<>();
        for (int s = 1; s <= 5; s++) { int n = s; screens.add(CompletableFuture.supplyAsync(() -> { await(300); return n * 10; })); }
        CompletableFuture.allOf(screens.toArray(new CompletableFuture[0])).join();
        int total = screens.stream().mapToInt(CompletableFuture::join).sum();
        System.out.println(t() + " total free seats: " + total);
        System.out.println("common pool parallelism: " + ForkJoinPool.commonPool().getParallelism());
    }
}
