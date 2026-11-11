import java.util.Scanner;

public class Fare {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Age: ");
        int age = Integer.parseInt(in.nextLine().strip());
        System.out.print("Day (mon, tue, wed, ...): ");
        String day = in.nextLine().strip().toLowerCase();

        double price;
        String fare;
        if (day.equals("wed")) {
            price = 6.00;
            fare = "Wednesday";
        } else if (age < 14) {
            price = 6.50;
            fare = "kids";
        } else if (age >= 65) {
            price = 7.00;
            fare = "senior";
        } else {
            price = 9.50;
            fare = "full price";
        }
        System.out.printf("Fare %s: $%.2f%n", fare, price);
    }
}
