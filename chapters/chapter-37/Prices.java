public class Prices {
    // version 8
    static int price(int age) {
        return age < 14 || age > 65 ? 700 : 950;
    }
    public static void main(String[] a) {
        if (price(65) != 700) { System.out.println("FAILED: age 65"); System.exit(1); }
        System.out.println("ok");
    }
}
