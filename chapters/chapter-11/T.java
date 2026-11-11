public class T {
    static long calls = 0;
    static long fib(int n) { calls++; return n < 2 ? n : fib(n - 1) + fib(n - 2); }
    public static void main(String[] a) {
        long t0 = System.nanoTime(); long r = fib(40); long ms = (System.nanoTime() - t0) / 1_000_000;
        System.out.println(r + " " + calls + " " + ms + " ms");
    }
}
