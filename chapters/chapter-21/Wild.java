import java.util.*;
public class Wild {
    static void addPrices(List<? super Integer> dest) { dest.add(950); dest.add(700); }
    static <T> void copy(List<? super T> dest, List<? extends T> src) { for (T x : src) dest.add(x); }
    static void print(List<?> anything) { System.out.println(anything.size() + " elements: " + anything); }
    public static void main(String[] args) {
        Object[] objects = new String[2];
        try { objects[0] = 42; } catch (ArrayStoreException e) { System.out.println("ArrayStoreException: " + e.getMessage()); }
        List<Number> numbers = new ArrayList<>();
        List<Object> objects2 = new ArrayList<>();
        addPrices(numbers); addPrices(objects2);
        System.out.println(numbers + " " + objects2);
        List<Number> dest = new ArrayList<>();
        copy(dest, List.of(1, 2)); copy(dest, List.of(3.5));
        System.out.println(dest);
        print(List.of("a", "b")); print(dest);
        List<String> ls = new ArrayList<>(); List<Integer> li = new ArrayList<>();
        System.out.println(ls.getClass() == li.getClass());
        List raw = ls;
        raw.add(99);
        try { String s = ls.get(0); } catch (ClassCastException e) { System.out.println("ClassCastException from the raw type"); }
    }
}
