import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Queue {
    static class Screen {
        private final Lock lock = new ReentrantLock();
        private int free = 10;
        boolean book(int n) throws InterruptedException {
            if (!lock.tryLock(100, TimeUnit.MILLISECONDS)) return false;   // gives up after 100 ms
            try {
                if (free < n) return false;
                free -= n;
                return true;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new Screen().book(3));
        BlockingQueue<String> orders = new ArrayBlockingQueue<>(5);
        Thread printer = Thread.ofPlatform().name("printer").start(() -> {
            try {
                while (true) {
                    String o = orders.take();              // waits if the queue is empty
                    if (o.equals("END")) break;
                    System.out.println("printing ticket " + o);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        for (String o : new String[]{"C5", "C6", "D1"}) orders.put(o);  // waits if the queue is full
        orders.put("END");
        printer.join();
    }
}
