public class Commas {
    public static void main(String[] args) {
        for (int i = 0, j = 10; i < j; i += 3, j -= 2) {
            System.out.print("(" + i + "," + j + ") ");
        }
        System.out.println();
        for (double x = 0; x != 1.0; x += 0.1) {
            System.out.print(x + " ");
            if (x > 2) { System.out.println("...infinite loop interrupted"); break; }
        }
        int k = 0;
        for (; k < 3; ) { k++; }
        System.out.println("k = " + k);
        for (int i = 0; i < 3; i++);
        { System.out.println("only once"); }
    }
}
