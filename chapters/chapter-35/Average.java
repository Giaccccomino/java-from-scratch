public class Average {
    static double averageOccupancy(int[] occupied, int capacity) {
        int sum = 0;
        for (int i = 0; i < occupied.length; i++) {
            sum += occupied[i];
        }
        return sum / occupied.length * 100.0 / capacity;   // line 7
    }

    public static void main(String[] args) {
        int[] occupied = {90, 75, 101, 60};
        System.out.printf("Average occupancy: %.1f%%%n", averageOccupancy(occupied, 120));
    }
}
