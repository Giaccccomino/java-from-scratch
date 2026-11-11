public class Search {
    public static void main(String[] args) {
        int capacity = 120;
        int booked = 0;
        int[] requests = {30, 25, 40, 35, 20};
        int i;
        for (i = 0; i < requests.length; i++) {
            if (booked + requests[i] > capacity) {
                System.out.println("Group " + (i + 1) + " rejected: it needs "
                        + requests[i] + " seats, only " + (capacity - booked) + " left");
                break;
            }
            booked += requests[i];
        }
        System.out.println("Booked: " + booked + " (groups accepted: " + i + ")");
        for (int n = 1; n <= 10; n++) {
            if (n % 3 == 0) continue;
            System.out.print(n + " ");
        }
        System.out.println();
    }
}
