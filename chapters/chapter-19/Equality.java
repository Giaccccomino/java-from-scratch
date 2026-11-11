import java.util.*;

public class Equality {
    public static void main(String[] args) {
        ScreenWithout a = new ScreenWithout("Screen 1", 120), b = new ScreenWithout("Screen 1", 120);
        System.out.println(a.equals(b));

        Screen s1 = new Screen("Screen 1", 120), s2 = new Screen("Screen 1", 120), s3 = new Screen("Screen 2", 80);
        System.out.println(s1.equals(s2) + " " + s1.equals(s3) + " " + s1.equals(null) + " " + s1.equals("Screen 1"));
        System.out.println((s1.hashCode() == s2.hashCode()) + " " + s1.hashCode());
        Set<Screen> screens = new HashSet<>(List.of(s1, s3));
        System.out.println(screens.contains(s2) + " " + screens.size());
        List<Screen> list = new ArrayList<>(List.of(s1, s3));
        System.out.println(list.contains(s2) + " " + list.indexOf(new Screen("Screen 2", 80)));

        OnlyEquals e1 = new OnlyEquals("x"), e2 = new OnlyEquals("x");
        Set<OnlyEquals> set = new HashSet<>();
        set.add(e1); set.add(e2);
        System.out.println(e1.equals(e2) + " " + set.size() + " " + set.contains(new OnlyEquals("x")));

        Overload o1 = new Overload("x"), o2 = new Overload("x");
        Object oo = o2;
        System.out.println(o1.equals(o2) + " " + o1.equals(oo) + " " + List.of(o1).contains(o2));

        Mutable m = new Mutable("Skyline");
        Set<Mutable> ms = new HashSet<>();
        ms.add(m);
        m.title = "Skyline restored";
        System.out.println(ms.contains(m) + " " + ms.size());
        System.out.println(Objects.hash("Screen 1", 120) + " " + Objects.equals(null, null) + " " + "Aa".hashCode() + " " + "BB".hashCode());
    }
}

class ScreenWithout { String n; int c; ScreenWithout(String n, int c) { this.n = n; this.c = c; } }

final class Screen {
    private final String name;
    private final int capacity;
    Screen(String name, int capacity) { this.name = name; this.capacity = capacity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Screen other)) return false;
        return capacity == other.capacity && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capacity);
    }
}

class OnlyEquals {
    final String v; OnlyEquals(String v) { this.v = v; }
    @Override public boolean equals(Object o) { return o instanceof OnlyEquals s && s.v.equals(v); }
}

class Overload {
    final String v; Overload(String v) { this.v = v; }
    public boolean equals(Overload o) { return o != null && o.v.equals(v); }
}

class Mutable {
    String title; Mutable(String t) { title = t; }
    @Override public boolean equals(Object o) { return o instanceof Mutable m && m.title.equals(title); }
    @Override public int hashCode() { return title.hashCode(); }
}
