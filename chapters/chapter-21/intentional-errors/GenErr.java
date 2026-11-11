import java.util.*;
public class GenErr {
    public static void main(String[] a) {
        Box<String> b = new Box<>("x");
        b.set(42);
        List<int> l;
        List<Object> lo = new ArrayList<String>();
    }
}
class Box<T> { T v; Box(T v) { this.v = v; } void set(T v) { this.v = v; } }
