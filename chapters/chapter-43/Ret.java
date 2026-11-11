import java.lang.annotation.*;
import java.lang.invoke.*;
import java.lang.reflect.*;
public class Ret {
    @interface NoRetention {}
    @NoRetention static class A {}
    public static int increment(int n) { return n + 1; }
    public static void main(String[] a) throws Throwable {
        System.out.println(A.class.getAnnotation(NoRetention.class));
        Method m = Ret.class.getMethod("increment", int.class);
        MethodHandle mh = MethodHandles.lookup().findStatic(Ret.class, "increment", MethodType.methodType(int.class, int.class));
        for (int round = 0; round < 3; round++) {
            long t0 = System.nanoTime(); int x = 0;
            for (int i = 0; i < 10_000_000; i++) x = increment(x);
            long t1 = System.nanoTime(); int y = 0;
            for (int i = 0; i < 10_000_000; i++) y = (Integer) m.invoke(null, y);
            long t2 = System.nanoTime(); int z = 0;
            for (int i = 0; i < 10_000_000; i++) z = (int) mh.invokeExact(z);
            long t3 = System.nanoTime();
            System.out.printf("direct %d ms, reflection %d ms, MethodHandle %d ms (%d %d %d)%n", (t1 - t0) / 1_000_000, (t2 - t1) / 1_000_000, (t3 - t2) / 1_000_000, x, y, z);
        }
    }
}
