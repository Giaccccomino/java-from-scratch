public class Table {
    public static void main(String[] args) {
        for (int r = 1; r <= 5; r++) {
            for (int c = 1; c <= 5; c++) {
                System.out.printf("%4d", r * c);
            }
            System.out.println();
        }
        long ops = 0;
        for (int i = 0; i < 1000; i++)
            for (int j = 0; j < 1000; j++)
                ops++;
        System.out.println("Operations: " + ops);
    }
}
