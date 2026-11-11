import java.util.Scanner;

public class Cashier {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Day (mon-sun): ");
        String day = in.nextLine().strip().toLowerCase();
        System.out.print("Age: ");
        String text = in.nextLine().strip();
        if (text.isEmpty()) {
            System.out.println("Age missing.");
            return;
        }
        int age = Integer.parseInt(text);
        if (age < 0 || age > 120) {
            System.out.println("Invalid age: " + age);
            return;
        }
        System.out.print("3D movie? (y/n): ");
        boolean is3D = in.nextLine().strip().equalsIgnoreCase("y");

        double base = switch (day) {
            case "wed" -> 6.00;
            case "sat", "sun" -> 10.50;
            case "mon", "tue", "thu", "fri" -> 9.50;
            default -> -1;
        };
        if (base < 0) {
            System.out.println("Invalid day: " + day);
            return;
        }
        String rate;
        double price = base;
        if (age < 3) {
            rate = "free";
            price = 0;
        } else if (age < 14 || age >= 65) {
            rate = "reduced";
            price = base - 2.50;
        } else {
            rate = "full";
        }
        boolean with3D = is3D && price > 0;
        if (with3D) {
            price += 2.00;
        }
        System.out.printf("%s rate%s: $%.2f%n", rate, with3D ? " + 3D" : "", price);
    }
}
