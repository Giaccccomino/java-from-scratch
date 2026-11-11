public class Collatz {
    public static void main(String[] args) {
        long n = 27;
        int steps = 0;
        long maximum = n;
        while (n != 1) {
            n = (n % 2 == 0) ? n / 2 : 3 * n + 1;
            maximum = Math.max(maximum, n);
            steps++;
        }
        System.out.println("Steps: " + steps + ", maximum reached: " + maximum);
    }
}
