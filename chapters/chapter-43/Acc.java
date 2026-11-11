import java.lang.reflect.*;
public class Acc {
    record Seat(char row, int number) {}
    public static void main(String[] a) throws Exception {
        Object s = new ExternalScreen();
        Field f = ExternalScreen.class.getDeclaredField("free");
        try { f.get(s); } catch (IllegalAccessException e) { System.out.println("IllegalAccessException: " + e.getMessage()); }
        f.setAccessible(true);
        System.out.println(f.get(s));
        Field n = Seat.class.getDeclaredField("number");
        n.setAccessible(true);
        try { n.set(new Seat('C', 5), 9); } catch (IllegalAccessException e) { System.out.println("IllegalAccessException: " + e.getMessage()); }
    }
}
