import java.util.Arrays;

public class Stats {
    public static void main(String[] args) {
        String[] days = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};
        int[] viewers = {312, 280, 455, 301, 520, 890, 760};

        int total = 0;
        int iMax = 0, iMin = 0;
        for (int i = 0; i < viewers.length; i++) {
            total += viewers[i];
            if (viewers[i] > viewers[iMax]) iMax = i;
            if (viewers[i] < viewers[iMin]) iMin = i;
        }
        double average = (double) total / viewers.length;
        System.out.printf("Week total: %d viewers, average %.1f%n", total, average);
        System.out.println("Best day: " + days[iMax] + " (" + viewers[iMax] + ")");
        System.out.println("Worst day: " + days[iMin] + " (" + viewers[iMin] + ")");
        System.out.print("Above average:");
        for (int i = 0; i < viewers.length; i++) {
            if (viewers[i] > average) System.out.print(" " + days[i]);
        }
        System.out.println();

        int[] sorted = viewers.clone();
        Arrays.sort(sorted);
        System.out.println("Sorted: " + Arrays.toString(sorted));
        System.out.println("Original untouched: " + Arrays.toString(viewers));
        int pos = Arrays.binarySearch(sorted, 455);
        System.out.println("455 at position " + pos + " of the sorted array");
        System.out.println("Search for 400: " + Arrays.binarySearch(sorted, 400));
    }
}
