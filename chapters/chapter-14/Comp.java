import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class Comp {
    public static void main(String[] args) {
        CountingList l = new CountingList();
        l.addAll(List.of("a", "b", "c"));
        System.out.println("additions counted: " + l.additions);
        java.util.Stack<String> s = new java.util.Stack<>();
        s.push("first"); s.push("second");
        s.add(0, "intruder");
        System.out.println(s);
    }
}
class CountingList extends ArrayList<String> {
    int additions = 0;
    @Override public boolean add(String e) { additions++; return super.add(e); }
    @Override public boolean addAll(Collection<? extends String> c) { additions += c.size(); return super.addAll(c); }
}
