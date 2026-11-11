public class Sealed {
    static String receipt(Payment p) {
        return switch (p) {
            case Cash c -> String.format("Cash: change $%.2f", (c.given() - c.amount()) / 100.0);
            case Card c -> "Card **** " + c.last4();
            case Voucher v -> "Voucher " + v.code();
        };
    }
    public static void main(String[] args) {
        Payment[] payments = {
            new Cash(1900, 2000), new Card(2700, "4821"), new Voucher(950, "AUR-77")
        };
        long total = 0;
        for (Payment p : payments) {
            System.out.println(receipt(p));
            total += p.amount();
        }
        System.out.printf("Total taken: $%.2f%n", total / 100.0);
    }
}

sealed interface Payment permits Cash, Card, Voucher {
    long amount();
}
record Cash(long amount, long given) implements Payment {}
record Card(long amount, String last4) implements Payment {}
record Voucher(long amount, String code) implements Payment {}
