public class Takings {
    public static void main(String[] args) {
        int showings = 4;
        double price = 9.50;
        double takings = 0;
        int totalTickets = 0;
        for (int s = 1; s <= showings; s++) {
            int sold = 40 + s * 15;
            totalTickets += sold;
            takings += sold * price;
            System.out.printf("Showing %d: %3d tickets%n", s, sold);
        }
        System.out.printf("Total: %d tickets, $%.2f%n", totalTickets, takings);
        System.out.printf("Average: %.1f tickets per showing%n", (double) totalTickets / showings);
    }
}
