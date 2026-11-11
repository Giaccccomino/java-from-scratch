import java.util.Scanner;
public class Robust {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("How many tickets? ");
        int quantity = Integer.parseInt(in.nextLine().strip());
        System.out.print("Viewer's name: ");
        String name = in.nextLine().strip();
        System.out.println(name + ": " + quantity + " tickets");
    }
}
