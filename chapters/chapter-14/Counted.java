import java.util.Collection;
import java.util.HashSet;
import java.util.List;
public class Counted {
    public static void main(String[] args) {
        CountingSet s = new CountingSet();
        s.addAll(List.of("Smith", "Johnson", "Brown"));
        System.out.println("Additions counted: " + s.getAdditions());
    }
}
class CountingSet extends HashSet<String> {
    private int additions = 0;
    @Override public boolean add(String e) { additions++; return super.add(e); }
    @Override public boolean addAll(Collection<? extends String> c) { additions += c.size(); return super.addAll(c); }
    int getAdditions() { return additions; }
}
