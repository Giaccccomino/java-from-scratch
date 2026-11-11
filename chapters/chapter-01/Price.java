public class Price {
    public static void main(String[] args) {
        double fullPrice = 9.50;
        int age = 12;

        double price = fullPrice;
        if (age < 14) {
            price = fullPrice * 0.7;   // 30% off for kids
        }

        System.out.printf("Ticket for a %d-year-old: $%.2f%n", age, price);
    }
}
