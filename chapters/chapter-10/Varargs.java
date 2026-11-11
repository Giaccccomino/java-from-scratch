public class Varargs {
    static int sum(int... values) {
        int s = 0;
        for (int v : values) s += v;
        return s;
    }
    public static void main(String[] args) {
        System.out.println(sum());
        System.out.println(sum(9));
        System.out.println(sum(55, 70, 85, 100));
        System.out.println(sum(new int[]{1, 2, 3}));
        System.out.printf("%s has %d seats%n", "Screen 1", 120);
        String s = String.join(" | ", "Screen 1", "Screen 2", "Screen 3");
        System.out.println(s);
    }
}
