import java.util.Arrays;

public class Value {
    static void doubleIt(int n) {
        n = n * 2;
        System.out.println("inside: " + n);
    }
    static void clear(boolean[] row) {
        Arrays.fill(row, false);
    }
    static void replace(boolean[] row) {
        row = new boolean[]{true, true, true};
        System.out.println("inside: " + Arrays.toString(row));
    }
    public static void main(String[] args) {
        int seats = 10;
        doubleIt(seats);
        System.out.println("outside: " + seats);
        boolean[] row = {true, false, true};
        clear(row);
        System.out.println("after clear: " + Arrays.toString(row));
        replace(row);
        System.out.println("after replace: " + Arrays.toString(row));
    }
}
