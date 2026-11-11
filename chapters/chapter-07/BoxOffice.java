import java.util.Scanner;

public class BoxOffice {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Viewer's name: ");
        String name = in.nextLine();

        System.out.print("How many tickets? ");
        int quantity = in.nextInt();

        System.out.print("Unit price: ");
        double price = in.nextDouble();

        double total = quantity * price;
        System.out.printf("%s, total to pay: $%.2f%n", name, total);
    }
}
