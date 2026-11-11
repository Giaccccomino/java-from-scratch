import java.util.Scanner;

public class Receipt {
    public static void main(String[] args) {
        final double FULL_PRICE = 9.50;
        final double REDUCED_PRICE = 7.00;

        Scanner in = new Scanner(System.in);

        System.out.print("Movie: ");
        String movie = in.nextLine().strip();
        System.out.print("Full-price tickets: ");
        int full = Integer.parseInt(in.nextLine().strip());
        System.out.print("Reduced-price tickets: ");
        int reduced = Integer.parseInt(in.nextLine().strip());

        double fullTotal = full * FULL_PRICE;
        double reducedTotal = reduced * REDUCED_PRICE;
        double total = fullTotal + reducedTotal;

        System.out.println();
        System.out.println("=".repeat(34));
        System.out.println("AURORA CINEMA");
        System.out.printf("Movie: %s%n", movie);
        System.out.println("-".repeat(34));
        System.out.printf("%-12s %4s %7s %8s%n", "Rate", "Qty", "Price", "Total");
        System.out.printf("%-12s %4d %7.2f %8.2f%n", "Full", full, FULL_PRICE, fullTotal);
        System.out.printf("%-12s %4d %7.2f %8.2f%n", "Reduced", reduced, REDUCED_PRICE, reducedTotal);
        System.out.println("-".repeat(34));
        System.out.printf("%-25s %8.2f%n", "TOTAL ($)", total);
        System.out.println("=".repeat(34));
    }
}
