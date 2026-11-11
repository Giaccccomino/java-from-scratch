import java.util.Scanner;

public class Age {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("Age: ");
            String line = in.nextLine().strip();
            try {
                int age = Integer.parseInt(line);
                System.out.println("Age recorded: " + age);
                break;
            } catch (NumberFormatException e) {
                System.out.println("\"" + line + "\" is not a whole number. Try again.");
            }
        }
    }
}
