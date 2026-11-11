import java.util.*;
public class BoxOffice {
    static final Map<Integer, byte[]> cache = new HashMap<>();   // the "cache" that is never emptied
    public static void main(String[] a) throws InterruptedException {
        for (int request = 0; ; request++) {
            cache.put(request, new byte[10_000]);
            if (request % 10 == 0) Thread.sleep(20);
        }
    }
}
