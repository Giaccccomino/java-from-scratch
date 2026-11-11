import java.util.Arrays;
public class Var {
    public static void main(String[] args) {
        var days = new String[]{"mon", "tue"};
        int[] a = new int[]{1, 2};
        int b[] = {3, 4};
        int[] c;
        c = new int[]{5, 6};
        System.out.println(days.length + Arrays.toString(a) + Arrays.toString(b) + Arrays.toString(c));
    }
}
