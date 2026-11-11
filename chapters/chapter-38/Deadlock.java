public class Deadlock {
    static class Screen {
        final String name; int free = 10;
        Screen(String name) { this.name = name; }
    }

    // moves n bookings from one screen to the other
    static void move(Screen from, Screen to, int n) {
        synchronized (from) {
            pause();
            // and now the second lock
            synchronized (to) {        // line 12
                from.free += n;
                to.free -= n;
            }
        }
    }

    static void pause() {
        try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public static void main(String[] args) {
        Screen one = new Screen("Screen 1"), two = new Screen("Screen 2");
        new Thread(() -> move(one, two, 2), "register-1").start();
        new Thread(() -> move(two, one, 3), "register-2").start();
    }
}
