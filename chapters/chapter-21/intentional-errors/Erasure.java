import java.util.*;
public class Erasure {
    static <T> T create() { return new T(); }
    static boolean e(Object o) { return o instanceof List<String>; }
    static <T> T[] arr(int n) { return new T[n]; }
    static void f(List<String> l) {}
    static void f(List<Integer> l) {}
    static void read(List<? extends Number> l) { l.add(5); }
    public static void main(String[] a) {}
}
