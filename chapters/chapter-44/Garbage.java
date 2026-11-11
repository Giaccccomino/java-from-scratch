public class Garbage {
    record Ticket(String seat, long price, byte[] qr) {}
    public static void main(String[] a) {
        long sold = 0;
        for (int i = 0; i < 2_000_000; i++) {
            Ticket t = new Ticket("C" + (i % 20), 950, new byte[1024]);
            sold += t.price();
        }
        System.out.println("takings: " + sold);
    }
}
