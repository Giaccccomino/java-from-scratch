public class Fin {
    static int f() {
        try { return 1; } finally { System.out.println("finally runs before the return"); }
    }
    @SuppressWarnings("finally")
    static int g() {
        try { throw new RuntimeException("lost"); } finally { return 2; }
    }
    public static void main(String[] a) { System.out.println(f()); System.out.println(g()); }
}
