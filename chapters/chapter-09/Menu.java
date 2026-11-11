import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int seats = 10;
        String choice;
        do {
            System.out.println("[1] Book  [2] Availability  [0] Quit");
            System.out.print("> ");
            choice = in.nextLine().strip();
            switch (choice) {
                case "1" -> {
                    if (seats > 0) {
                        seats--;
                        System.out.println("Booked. " + seats + " seats left.");
                    } else {
                        System.out.println("Sold out.");
                    }
                }
                case "2" -> System.out.println("Free seats: " + seats);
                case "0" -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice.");
            }
        } while (!choice.equals("0"));
    }
}
