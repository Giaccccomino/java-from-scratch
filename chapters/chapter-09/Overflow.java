public class Overflow {
    public static void main(String[] args) {
        int laps = 0;
        for (byte b = 120; b > 0; b++) laps++;
        System.out.println("Laps: " + laps);
    }
}
