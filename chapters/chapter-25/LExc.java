import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;
public class LExc {
    @FunctionalInterface interface IOFunction<T, R> { R apply(T t) throws IOException; }
    static <T, R> Function<T, R> unchecked(IOFunction<T, R> f) {
        return t -> { try { return f.apply(t); } catch (IOException e) { throw new UncheckedIOException(e); } };
    }
    String name = "outer";
    void test() {
        Runnable lam = () -> System.out.println("lambda: " + this.name + " " + this.getClass().getSimpleName());
        Runnable anon = new Runnable() { String name = "anonymous"; public void run() { System.out.println("anonymous: " + this.name + " " + this.getClass().getName()); } };
        lam.run(); anon.run();
    }
    public static void main(String[] args) {
        new LExc().test();
        List<String> files = List.of("a.txt", "b.txt");
        try {
            files.stream().map(unchecked(p -> Files.readString(Path.of(p)))).forEach(System.out::println);
        } catch (UncheckedIOException e) {
            System.out.println("UncheckedIOException, cause: " + e.getCause().getClass().getSimpleName());
        }
    }
}
