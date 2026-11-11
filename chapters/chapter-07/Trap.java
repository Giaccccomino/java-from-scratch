import java.util.Scanner;
public class Trap {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("How many tickets? ");
        int quantity = in.nextInt();
        System.out.print("Viewer's name: ");
        String name = in.nextLine();
        System.out.println("[" + name + "] " + quantity);
    }
}
