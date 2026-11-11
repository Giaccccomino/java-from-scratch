public class Factory {
    public static void main(String[] args) {
        Seat a = Seat.of("C5");
        Seat b = Seat.at(2, 4);
        System.out.println(a + " " + b + " " + a.getRow() + " " + a.getNumber());
        System.out.println(Integer.valueOf(127) == Integer.valueOf(127));
        System.out.println(Integer.valueOf(128) == Integer.valueOf(128));
        try { Seat.of("Z99x"); } catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
    }
}
final class Seat {
    private final int row, number;
    private Seat(int row, int number) { this.row = row; this.number = number; }
    static Seat at(int row, int number) { return new Seat(row, number); }
    static Seat of(String code) {
        if (!code.matches("[A-Z][0-9]{1,2}")) throw new IllegalArgumentException("invalid seat code: " + code);
        return new Seat(code.charAt(0) - 'A', Integer.parseInt(code.substring(1)) - 1);
    }
    int getRow() { return row; }
    int getNumber() { return number; }
    @Override public String toString() { return "" + (char) ('A' + row) + (number + 1); }
}
