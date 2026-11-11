public class ShortCircuit {
    public static void main(String[] args) {
        int sold = 0, takings = 0;
        boolean ok = sold > 0 && takings / sold > 8;
        System.out.println(ok);
        boolean ko = sold > 0 & takings / sold > 8;      // ArithmeticException: & doesn't short-circuit
    }
}
