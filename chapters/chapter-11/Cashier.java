public class Cashier {
    static final double FULL_PRICE = 9.50;

    static double ticketPrice(int age, boolean wednesday) {
        if (wednesday) {
            return 6.00;
        }
        if (age < 3) {
            return 0;
        }
        if (age < 14 || age >= 65) {
            return FULL_PRICE - 2.50;
        }
        return FULL_PRICE;
    }

    static void printLine(String description, double amount) {
        System.out.printf("%-20s %8.2f%n", description, amount);
    }

    public static void main(String[] args) {
        double total = 0;
        int[] ages = {40, 38, 10, 2};
        for (int a : ages) {
            double p = ticketPrice(a, false);
            printLine("Ticket, age " + a, p);
            total += p;
        }
        printLine("TOTAL", total);
    }
}
