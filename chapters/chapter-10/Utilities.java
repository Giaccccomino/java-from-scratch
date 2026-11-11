import java.util.Arrays;

public class Utilities {
    public static void main(String[] args) {
        int[] v = {5, 3, 8, 1};
        int[] copy = Arrays.copyOf(v, 6);
        int[] piece = Arrays.copyOfRange(v, 1, 3);
        System.out.println(Arrays.toString(copy) + " " + Arrays.toString(piece));
        int[] dest = new int[6];
        System.arraycopy(v, 0, dest, 2, 4);
        System.out.println(Arrays.toString(dest));
        char[] row = new char[8];
        Arrays.fill(row, '-');
        row[3] = 'X';
        System.out.println(new String(row));
        String[] movies = {"Skyline", "The Big River", "A Night in the City", "harbor"};
        Arrays.sort(movies);
        System.out.println(Arrays.toString(movies));
        Arrays.sort(movies, String.CASE_INSENSITIVE_ORDER);
        System.out.println(Arrays.toString(movies));
        System.out.println(Arrays.stream(v).sum() + " " + Arrays.stream(v).max().getAsInt());
        int[] empty = {};
        System.out.println(empty.length);
        int[] reversed = new int[v.length];
        for (int i = 0; i < v.length; i++) reversed[i] = v[v.length - 1 - i];
        System.out.println(Arrays.toString(reversed));
        for (int i = 0, j = v.length - 1; i < j; i++, j--) { int t = v[i]; v[i] = v[j]; v[j] = t; }
        System.out.println(Arrays.toString(v));
        System.out.println(Arrays.hashCode(new int[]{1,2}) + " " + Arrays.compare(new int[]{1,2}, new int[]{1,3}) + " " + Arrays.mismatch(new int[]{1,2,3}, new int[]{1,2,4}));
    }
}
