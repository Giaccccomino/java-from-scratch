import java.time.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Pinning {
    static void withSynchronized() {
        Object lock = new Object();                       // a different lock for each task: no contention
        synchronized (lock) { await500(); }
    }
    static void withReentrantLock() {
        Lock lock = new ReentrantLock();
        lock.lock();
        try { await500(); } finally { lock.unlock(); }
    }
    static void await500() {
        try { Thread.sleep(Duration.ofMillis(500)); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
    static long trial(Runnable r) {
        long t0 = System.nanoTime();
        try (var ex = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 20; i++) ex.submit(r);
        }
        return (System.nanoTime() - t0) / 1_000_000;
    }
    public static void main(String[] args) {
        System.out.println("processors: " + Runtime.getRuntime().availableProcessors());
        System.out.println("20 tasks with synchronized: " + trial(Pinning::withSynchronized) + " ms");
        System.out.println("20 tasks with ReentrantLock: " + trial(Pinning::withReentrantLock) + " ms");
    }
}
