public class Unnamed {
    record Seat(char row, int number) {}
    public static void main(String[] a) {
        Object o = new Seat('A', 3);
        if (o instanceof Seat(char r, _)) System.out.println("row " + r);
    }
}
