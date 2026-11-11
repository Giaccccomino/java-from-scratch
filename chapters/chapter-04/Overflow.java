public class Overflow {
    public static void main(String[] args) {
        int maximum = Integer.MAX_VALUE;
        System.out.println(maximum);
        System.out.println(maximum + 1);
        int takingsCents = 950 * 3_000_000;
        System.out.println(takingsCents);
        long correct = 950L * 3_000_000;
        System.out.println(correct);
        System.out.println(Math.addExact(maximum, 0));
        try { Math.multiplyExact(950, 3_000_000); } catch (ArithmeticException e) { System.out.println(e); }
    }
}
