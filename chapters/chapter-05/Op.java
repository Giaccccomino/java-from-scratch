public class Op {
    public static void main(String[] args) {
        System.out.println(17 / 5 + " " + 17 % 5 + " " + -17 / 5 + " " + -17 % 5);
        System.out.println(Math.floorDiv(-17, 5) + " " + Math.floorMod(-17, 5));
        System.out.println(7.5 % 2);
        int duration = 137;
        System.out.println(duration / 60 + " h " + duration % 60 + " min");
        int start = 20 * 60 + 45; int end = start + duration;
        System.out.printf("End: %02d:%02d%n", end / 60 % 24, end % 60);
        int x = 5; int a = x++; int b = ++x; System.out.println(a + " " + b + " " + x);
        int v = 10; v += 2.7; System.out.println(v);
        short s = 10; s *= 1000; s *= 10; System.out.println(s);
        System.out.println(1 + 2 + " dollars");
        System.out.println("dollars " + 1 + 2);
        System.out.println("dollars " + (1 + 2));
        System.out.println('A' + 'B');
        System.out.println("" + 'A' + 'B');
        int age = 70; boolean reduced = age < 14 || age >= 65; System.out.println(reduced);
        String price = age >= 65 ? "reduced" : "full"; System.out.println(price);
        System.out.println(0.1 + 0.2 == 0.3);
        System.out.println(Math.abs(0.1 + 0.2 - 0.3) < 1e-9);
        System.out.println(Math.pow(2, 10) + " " + Math.sqrt(144) + " " + Math.ceil(4.1) + " " + Math.floor(4.9) + " " + Math.max(3, 8) + " " + Math.abs(-5));
        System.out.println(Math.clamp(130, 0, 120));
        System.out.println(6 & 3);
        System.out.println(6 | 3);
        System.out.println(6 ^ 3);
        System.out.println(~6);
        System.out.println(1 << 4);
        System.out.println(-16 >> 2);
        System.out.println(-16 >>> 28);
    }
}
