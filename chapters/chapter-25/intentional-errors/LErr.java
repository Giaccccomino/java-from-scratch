import java.util.function.*;
public class LErr {
    public static void main(String[] a) {
        var f = x -> x * 2;
        Object o = () -> {};
        int base = 10;
        base++;
        IntUnaryOperator g = x -> x + base;
        Function<String, String> h = s -> { s.trim(); };
    }
}
