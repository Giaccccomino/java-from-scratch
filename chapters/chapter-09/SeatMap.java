public class SeatMap {
    public static void main(String[] args) {
        int rows = 4, seatsPerRow = 8;
        for (int r = 0; r < rows; r++) {
            char letter = (char) ('A' + r);
            System.out.print(letter + " ");
            for (int s = 1; s <= seatsPerRow; s++) {
                boolean taken = (r * seatsPerRow + s) % 5 == 0;
                System.out.print(taken ? "[X]" : "[ ]");
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int s = 1; s <= seatsPerRow; s++) System.out.printf(" %d ", s);
        System.out.println();
    }
}
