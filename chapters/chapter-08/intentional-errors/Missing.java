public class Missing {
    enum Day { MON, TUE, WED, THU, FRI, SAT, SUN }
    public static void main(String[] args) {
        Day d = Day.WED;
        double price = switch (d) {
            case WED -> 6.00;
            case SAT, SUN -> 10.50;
        };
        int n = 3;
        String s = switch (n) {
            case 1 -> "one";
            case 2 -> "two";
        };
    }
}
