public class Countdown {
    public static void main(String[] args) {
        int seconds = 5;
        while (seconds > 0) {
            System.out.print(seconds + "... ");
            seconds--;
        }
        System.out.println("Showtime!");
    }
}
