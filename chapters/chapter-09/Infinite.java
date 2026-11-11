// Never ends: i is never incremented. Stop it with Ctrl+C.
public class Infinite {
    public static void main(String[] args) {
        int i = 0;
        while (i < 3) {
            System.out.println("i = " + i);
        }
    }
}
