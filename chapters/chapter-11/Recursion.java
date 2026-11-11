public class Recursion {
    static long factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }
    static long fib(int n) {
        return n < 2 ? n : fib(n - 1) + fib(n - 2);
    }
    static long fibIter(int n) {
        long a = 0, b = 1;
        for (int i = 0; i < n; i++) { long t = a + b; a = b; b = t; }
        return a;
    }
    static int depth = 0;
    static void down() { depth++; down(); }
    public static void main(String[] args) {
        System.out.println(factorial(5) + " " + factorial(20) + " " + factorial(21));
        long t0 = System.nanoTime();
        System.out.println(fib(40));
        long t1 = System.nanoTime();
        System.out.println(fibIter(40) + " " + fibIter(90));
        System.out.println("recursive is slow: " + ((t1 - t0) / 1_000_000 > 50));
        try { down(); } catch (StackOverflowError e) { System.out.println("StackOverflowError after about " + (depth / 1000) * 1000 + " calls"); }
    }
}
