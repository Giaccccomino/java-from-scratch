import java.util.*;

public class Generics {
    public static void main(String[] args) {
        ObjectBox b = new ObjectBox("Skyline");
        String t = (String) b.get();
        b.set(42);
        try { String u = (String) b.get(); } catch (ClassCastException e) { System.out.println("ClassCastException at run time"); }

        Box<String> title = new Box<>("A Night in the City");
        String x = title.get();
        System.out.println(x.toUpperCase());

        Pair<String, Integer> p = new Pair<>("Screen 1", 120);
        System.out.println(p + " " + p.first().length() + " " + (p.second() + 1));
        var sw = p.swapped();
        System.out.println(sw);

        String[] movies = {"Skyline", "The Big River", "A Night in the City"};
        Integer[] numbers = {312, 890, 455};
        System.out.println(first(movies) + " " + first(numbers) + " " + Generics.<String>first(movies));
        System.out.println(max(numbers) + " " + max(movies));
        System.out.println(sum(List.of(1, 2, 3)) + " " + sum(List.of(9.5, 7.0)));

        Integer a = 127, c = 127, d = 128, e = 128;
        System.out.println((a == c) + " " + (d == e) + " " + d.equals(e));
        int n = a;
        List<Integer> list = new ArrayList<>(List.of(10, 20, 30));
        list.remove(1);
        System.out.println(list);
        list.remove(Integer.valueOf(10));
        System.out.println(list);
        Integer nothing = null;
        try { int z = nothing; } catch (NullPointerException ex) { System.out.println("NPE: " + ex.getMessage()); }
        long t0 = System.nanoTime();
        Long total = 0L;
        for (int i = 0; i < 10_000_000; i++) total += i;
        long t1 = System.nanoTime();
        long total2 = 0;
        for (int i = 0; i < 10_000_000; i++) total2 += i;
        long t2 = System.nanoTime();
        System.out.println(total.equals(total2) + " boxing slower: " + ((t1 - t0) > (t2 - t1)));
        System.out.println((t1 - t0) / 1_000_000 + " ms vs " + (t2 - t1) / 1_000_000 + " ms");
    }

    static <T> T first(T[] elements) { return elements[0]; }

    static <T extends Comparable<T>> T max(T[] elements) {
        T max = elements[0];
        for (T x : elements) if (x.compareTo(max) > 0) max = x;
        return max;
    }

    static double sum(List<? extends Number> numbers) {
        double s = 0;
        for (Number n : numbers) s += n.doubleValue();
        return s;
    }
}

class ObjectBox { private Object v; ObjectBox(Object v) { this.v = v; } Object get() { return v; } void set(Object v) { this.v = v; } }

class Box<T> {
    private T value;
    Box(T value) { this.value = value; }
    T get() { return value; }
    void set(T value) { this.value = value; }
}

record Pair<A, B>(A first, B second) {
    Pair<B, A> swapped() { return new Pair<>(second, first); }
}
