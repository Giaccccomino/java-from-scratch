import java.util.Scanner;

public class Sum {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int sum = 0, count = 0, max = Integer.MIN_VALUE;
        while (true) {
            System.out.print("Tickets sold (empty to finish): ");
            String line = in.nextLine().strip();
            if (line.isEmpty()) break;
            int n;
            try { n = Integer.parseInt(line); } catch (NumberFormatException e) { System.out.println("Not a number."); continue; }
            if (n < 0) { System.out.println("Negative value ignored."); continue; }
            sum += n; count++;
            if (n > max) max = n;
        }
        if (count == 0) System.out.println("No data.");
        else System.out.printf("Showings: %d, total: %d, average: %.2f, maximum: %d%n", count, sum, (double) sum / count, max);
    }
}
