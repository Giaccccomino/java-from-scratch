import java.util.Arrays;

public class Basics {
    public static void main(String[] args) {
        int[] sold = new int[4];
        System.out.println(Arrays.toString(sold));
        sold[0] = 55;
        sold[3] = 100;
        System.out.println(Arrays.toString(sold) + " length " + sold.length);
        String[] movies = {"A Night in the City", "The Big River", "Skyline"};
        double[] prices = new double[2];
        boolean[] full = new boolean[2];
        String[] titles = new String[2];
        char[] c = new char[2];
        System.out.println(Arrays.toString(prices) + Arrays.toString(full) + Arrays.toString(titles) + (int) c[0]);
        System.out.println(movies[movies.length - 1]);
        System.out.println(sold);
        int total = 0;
        for (int v : sold) total += v;
        System.out.println("Total " + total);
        int[] a = {1, 2, 3};
        int[] b = a;
        b[0] = 99;
        System.out.println(Arrays.toString(a));
        int[] x = {1, 2, 3}, y = {1, 2, 3};
        System.out.println((x == y) + " " + x.equals(y) + " " + Arrays.equals(x, y));
    }
}
