import java.util.*;

public class Overbooking {
    static void pause() {
        try { Thread.sleep(1); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    static class Screen {
        private int free;
        Screen(int capacity) { free = capacity; }

        boolean book(int n) {               // NOT thread-safe
            if (free >= n) {
                pause();                       // simulates the card check
                free -= n;
                return true;
            }
            return false;
        }
        int free() { return free; }
    }

    public static void main(String[] args) throws InterruptedException {
        int trials = 0, overbooked = 0;
        for (int p = 0; p < 200; p++) {
            Screen screen = new Screen(1);
            List<Thread> customers = new ArrayList<>();
            for (int c = 0; c < 4; c++) customers.add(new Thread(() -> screen.book(1)));
            customers.forEach(Thread::start);
            for (Thread t : customers) t.join();
            trials++;
            if (screen.free() < 0) overbooked++;
        }
        System.out.println("Trials: " + trials + ", screens overbooked: " + overbooked);
    }
}
