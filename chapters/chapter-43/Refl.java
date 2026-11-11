import java.lang.reflect.*;
import java.util.*;

public class Refl {
    public static class Screen {
        private final String name;
        private int free;
        public static final int MAXIMUM = 500;

        public Screen(String name, int free) { this.name = name; this.free = free; }
        public String getName() { return name; }
        public int getFree() { return free; }
        public void book(int n) { if (n > free) throw new IllegalStateException("not enough seats"); free -= n; }
        private void reset() { free = 0; }
        @Override public String toString() { return name + " (" + free + " free)"; }
    }

    record Seat(char row, int number) {}

    public static void main(String[] args) throws Exception {
        Class<?> c1 = Screen.class;
        Class<?> c2 = new Screen("Screen 1", 120).getClass();
        Class<?> c3 = Class.forName("Refl$Screen");
        System.out.println(c1 == c2 && c2 == c3);
        System.out.println(c1.getName() + " | " + c1.getSimpleName() + " | " + c1.getSuperclass().getSimpleName() + " | " + Modifier.toString(c1.getModifiers()));

        System.out.println("-- fields");
        for (Field f : c1.getDeclaredFields()) System.out.println(Modifier.toString(f.getModifiers()) + " " + f.getType().getSimpleName() + " " + f.getName());
        System.out.println("-- declared methods");
        Arrays.stream(c1.getDeclaredMethods()).map(m -> m.getName() + Arrays.toString(m.getParameterTypes())).sorted().forEach(System.out::println);
        System.out.println("public methods (inherited too): " + c1.getMethods().length);
        System.out.println("-- constructors");
        for (Constructor<?> k : c1.getConstructors()) System.out.println(k);

        Constructor<?> k = c1.getConstructor(String.class, int.class);
        Object screen = k.newInstance("Screen 2", 80);
        Method book = c1.getMethod("book", int.class);
        book.invoke(screen, 3);
        System.out.println(screen);
        System.out.println(c1.getMethod("getFree").invoke(screen));

        try { book.invoke(screen, 1000); }
        catch (InvocationTargetException e) { System.out.println("InvocationTargetException, cause: " + e.getCause()); }

        try { c1.getMethod("book", String.class); }
        catch (NoSuchMethodException e) { System.out.println("NoSuchMethodException: " + e.getMessage()); }

        Field free = c1.getDeclaredField("free");
        try { free.get(screen); System.out.println("read without setAccessible"); }
        catch (IllegalAccessException e) { System.out.println("IllegalAccessException: " + e.getMessage()); }
        free.setAccessible(true);
        free.set(screen, 999);
        System.out.println(screen);

        Field name = c1.getDeclaredField("name");
        name.setAccessible(true);
        name.set(screen, "Secret Screen");
        System.out.println(screen);

        System.out.println(c1.getField("MAXIMUM").get(null));

        System.out.println("-- record");
        for (RecordComponent rc : Seat.class.getRecordComponents())
            System.out.println(rc.getName() + ": " + rc.getType() + " -> " + rc.getAccessor().invoke(new Seat('C', 5)));
        System.out.println(Seat.class.isRecord());

        // an attempt on a JDK class
        Field value = String.class.getDeclaredField("value");
        try { value.setAccessible(true); }
        catch (InaccessibleObjectException e) { System.out.println(e.getMessage()); }
    }
}
