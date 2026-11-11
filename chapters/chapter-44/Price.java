public class Price {
    static int price(int age) {
        if (age < 14 || age >= 65) return 700;
        return 950;
    }
    public static void main(String[] args) {
        int total = 0;
        for (int i = 0; i < 3; i++) total += price(10 + i * 30);
        System.out.println(total);
    }
}
