public class Multi {
    static int averagePrice(String[] prices, int count) {
        int sum = 0;
        for (int i = 0; i < count; i++) sum += Integer.parseInt(prices[i]);
        return sum / count;
    }
    public static void main(String[] args) {
        String[][] cases = { {"950", "700"}, {"950", "abc"}, {}, {"950"} };
        int[] counts = {2, 2, 0, 3};
        for (int k = 0; k < cases.length; k++) {
            try {
                System.out.println("Average: " + averagePrice(cases[k], counts[k]));
            } catch (NumberFormatException e) {
                System.out.println("Invalid price: " + e.getMessage());
            } catch (ArithmeticException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Incomplete data: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            } finally {
                System.out.println("  (case " + k + " processed)");
            }
        }
    }
}
