public class OutOfBounds {
    public static void main(String[] args) {
        int[] sold = {55, 70, 85, 100};
        for (int i = 0; i <= sold.length; i++) {
            System.out.println(sold[i]);
        }
    }
}
