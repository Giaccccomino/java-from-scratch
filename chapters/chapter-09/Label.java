public class Label {
    public static void main(String[] args) {
        int rows = 4, seatsPerRow = 8;
        int foundRow = -1;
        search:
        for (int r = 0; r < rows; r++) {
            for (int s = 4; s <= 5; s++) {
                boolean taken = (r * seatsPerRow + s) % 5 == 0;
                if (taken) {
                    continue search;
                }
            }
            foundRow = r;
            break;
        }
        if (foundRow >= 0) {
            System.out.println("Middle seats 4-5 free in row " + (char) ('A' + foundRow));
        } else {
            System.out.println("No row with the middle seats free");
        }
    }
}
