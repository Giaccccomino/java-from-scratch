public class Expression {
    enum Day { MON, TUE, WED, THU, FRI, SAT, SUN }

    public static void main(String[] args) {
        for (Day d : Day.values()) {
            double price = switch (d) {
                case WED -> 6.00;
                case SAT, SUN -> 10.50;
                case MON, TUE, THU, FRI -> 9.50;
            };
            System.out.printf("%s %.2f%n", d, price);
        }
    }
}
