import java.util.*;
import java.util.stream.*;
public class Par {
    public static void main(String[] a) {
        for (int g = 0; g < 5; g++) {
            List<Integer> l = new ArrayList<>();
            try { IntStream.range(0, 1_000_000).parallel().forEach(l::add); System.out.println(l.size()); }
            catch (Exception e) { System.out.println(e.getClass().getSimpleName()); }
        }
        System.out.println(IntStream.range(0, 1_000_000).parallel().boxed().toList().size());
    }
}
