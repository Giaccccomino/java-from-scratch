import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

public class Annot {
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.RECORD_COMPONENT})
    @interface NotBlank {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.RECORD_COMPONENT})
    @interface Range {
        int min() default 0;
        int max();
        String message() default "out of range";
    }

    record Booking(@NotBlank String customer,
                   @NotBlank String seat,
                   @Range(min = 1, max = 10, message = "1 to 10 tickets") int tickets) {}

    static List<String> validate(Record object) throws ReflectiveOperationException {
        List<String> errors = new ArrayList<>();
        for (RecordComponent rc : object.getClass().getRecordComponents()) {
            Object value = rc.getAccessor().invoke(object);
            if (rc.isAnnotationPresent(NotBlank.class) && (value == null || value.toString().isBlank()))
                errors.add(rc.getName() + ": required");
            Range r = rc.getAnnotation(Range.class);
            if (r != null) {
                int v = (Integer) value;
                if (v < r.min() || v > r.max()) errors.add(rc.getName() + ": " + r.message() + " (found " + v + ")");
            }
        }
        return errors;
    }

    // a mini test framework
    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD) @interface Check {}

    static class ScreenChecks {
        @Check void bookingReducesSeats() { if (3 != 3) throw new AssertionError(); }
        @Check void negativeSeatsRejected() { throw new AssertionError("no exception for -1"); }
        @Check void division() { int x = 1 / 0; }
        void helper() {}
    }

    static void run(Class<?> type) throws ReflectiveOperationException {
        int passed = 0, failed = 0;
        Method[] methods = type.getDeclaredMethods();
        Arrays.sort(methods, Comparator.comparing(Method::getName));
        for (Method m : methods) {
            if (!m.isAnnotationPresent(Check.class)) continue;
            Object instance = type.getDeclaredConstructor().newInstance();   // a new object per check
            try {
                m.setAccessible(true);
                m.invoke(instance);
                System.out.println("  OK    " + m.getName()); passed++;
            } catch (InvocationTargetException e) {
                System.out.println("  FAIL  " + m.getName() + ": " + e.getCause()); failed++;
            }
        }
        System.out.println(passed + " passed, " + failed + " failed");
    }

    // dynamic proxy
    interface PaymentService { boolean charge(String card, long cents); }

    @SuppressWarnings("unchecked")
    static <T> T withLogging(T target, Class<T> iface) {
        InvocationHandler h = (proxy, method, arguments) -> {
            long t0 = System.nanoTime();
            try {
                Object r = method.invoke(target, arguments);
                System.out.printf("%s%s -> %s (%d ms)%n", method.getName(), Arrays.toString(arguments), r, (System.nanoTime() - t0) / 1_000_000);
                return r;
            } catch (InvocationTargetException e) { throw e.getCause(); }
        };
        return (T) Proxy.newProxyInstance(iface.getClassLoader(), new Class<?>[]{iface}, h);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(validate(new Booking("Julia", "C5", 2)));
        System.out.println(validate(new Booking(" ", null, 12)));
        run(ScreenChecks.class);
        PaymentService real = (card, c) -> { try { Thread.sleep(120); } catch (InterruptedException e) {} return c < 10_000; };
        PaymentService traced = withLogging(real, PaymentService.class);
        traced.charge("4821", 2850);
        traced.charge("4821", 25_000);
        System.out.println(traced.getClass().getName() + " " + Proxy.isProxyClass(traced.getClass()));
        System.out.println(Booking.class.getRecordComponents()[2].getAnnotation(Range.class));
    }
}
