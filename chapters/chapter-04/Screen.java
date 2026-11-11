public class Screen {
    public static void main(String[] args) {
        int seats = 120;
        int sold;
        sold = 87;

        int free = seats - sold;
        System.out.println("Free seats: " + free);

        sold = sold + 5;          // 5 more tickets sold
        free = seats - sold;
        System.out.println("Free seats: " + free);
    }
}
