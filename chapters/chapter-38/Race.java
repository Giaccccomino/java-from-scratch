public class Race {
    static int tickets = 0;

    public static void main(String[] args) throws InterruptedException {
        Runnable register = () -> {
            for (int i = 0; i < 100_000; i++) tickets++;
        };
        Thread t1 = new Thread(register, "register-1");
        Thread t2 = new Thread(register, "register-2");
        t1.start(); t2.start();
        t1.join();  t2.join();
        System.out.println("Tickets sold: " + tickets);
    }
}
