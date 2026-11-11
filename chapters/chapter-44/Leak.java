import java.util.*;
public class Leak {
    static final List<byte[]> history = new ArrayList<>();      // grows forever
    public static void main(String[] a) {
        try {
            while (true) history.add(new byte[1024 * 1024]);
        } catch (OutOfMemoryError e) {
            int kept = history.size();
            history.clear();
            System.out.println("OutOfMemoryError after " + kept + " MB: " + e.getMessage());
        }
    }
}
