public class FloatingPoint {
    public static void main(String[] args) {
        System.out.println(0.1 + 0.2);
        System.out.println(9.50 * 3);
        System.out.println(0.1f + 0.2f);
        System.out.println(1.0 / 3);
        System.out.println(1.0f / 3);
        System.out.println(10.0 / 0);
        System.out.println(0.0 / 0);
        System.out.println(1.1 * 3);
        double total = 0; for (int i = 0; i < 10; i++) total += 0.1; System.out.println(total);
        System.out.println(new java.math.BigDecimal("0.1").add(new java.math.BigDecimal("0.2")));
    }
}
