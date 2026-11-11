import java.util.Arrays;

public class SeatPlan {
    public static void main(String[] args) {
        boolean[][] taken = new boolean[5][10];   // 5 rows of 10 seats
        taken[0][4] = true;
        taken[2][3] = true;
        taken[2][4] = true;
        taken[2][5] = true;
        taken[4][0] = true;

        int free = 0;
        for (int r = 0; r < taken.length; r++) {
            System.out.print((char) ('A' + r) + " ");
            for (int s = 0; s < taken[r].length; s++) {
                System.out.print(taken[r][s] ? "[X]" : "[ ]");
                if (!taken[r][s]) free++;
            }
            System.out.println();
        }
        int rows = taken.length, cols = taken[0].length;
        System.out.printf("Free: %d of %d (%.0f%%)%n", free, rows * cols, 100.0 * free / (rows * cols));

        int[] perRow = new int[rows];
        for (int r = 0; r < rows; r++)
            for (boolean x : taken[r])
                if (x) perRow[r]++;
        System.out.println("Taken per row: " + Arrays.toString(perRow));
    }
}
