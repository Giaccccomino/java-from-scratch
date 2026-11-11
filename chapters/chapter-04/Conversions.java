public class Conversions {
    public static void main(String[] args) {
        int seats = 120;
        long seatsL = seats;          // automatic widening
        double seatsD = seats;
        System.out.println(seatsL + " " + seatsD);

        double average = 87.9;
        int rounded = (int) average;           // truncation
        System.out.println(rounded);
        System.out.println(Math.round(average));
        System.out.println((int) -87.9);
        System.out.println((int) Math.round(87.5) + " " + Math.round(-87.5));

        long big = 3_000_000_000L;
        int small = (int) big;
        System.out.println(small);
        byte b = (byte) 200;
        System.out.println(b);

        int sold = 87, screens = 2;
        System.out.println(sold / screens);
        System.out.println((double) sold / screens);
        System.out.println((double) (sold / screens));

        long l = 123456789012345L;
        float f = l;
        System.out.println(f);
        int i = 16_777_217; float g = i; System.out.println(g);

        byte x = 10, y = 20;
        // byte z = x + y;  // error
        int z = x + y;
        System.out.println(z);
        short s = 10; s += 5; System.out.println(s);
        char letter = 'C'; int code = letter; System.out.println(code);
    }
}
