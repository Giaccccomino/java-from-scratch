public class SwErr {
    static String f(Payment p) { return switch (p) { case Cash c -> "c"; case Card c -> "k"; }; }
    public static void main(String[] a) {}
}
sealed interface Payment permits Cash, Card, Voucher {}
record Cash(long i) implements Payment {}
record Card(long i) implements Payment {}
record Voucher(long i) implements Payment {}
