public class Literals {
    public static void main(String[] args) {
        long viewersPerYear = 1_250_000L;
        long views = 3_000_000_000L;
        int hexadecimal = 0xFF;
        int binary = 0b1010;
        float priceF = 9.50f;
        double price = 9.50;
        double large = 1.5e6;
        char row = 'F';
        char euro = '€';
        boolean threeD = true;
        System.out.println(viewersPerYear + " " + views + " " + hexadecimal + " " + binary);
        System.out.println(priceF + " " + price + " " + large + " " + row + " " + euro + " " + threeD);
        System.out.println(row + 1);
        System.out.println((char) (row + 1));
        char c = 'A'; c++; System.out.println(c);
    }
}
