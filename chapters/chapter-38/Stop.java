public class Stop {
    static boolean open = true;

    public static void main(String[] args) throws InterruptedException {
        Thread display = new Thread(() -> {
            long loops = 0;
            while (open) loops++;
            System.out.println("display off after " + loops + " loops");
        });
        display.start();
        Thread.sleep(500);
        open = false;
        System.out.println("closing requested");
        display.join(2000);           // waits at most 2 seconds
        System.out.println(display.isAlive() ? "the display is still on!" : "done");
        System.exit(0);
    }
}
