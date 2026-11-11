public class Twr {
    public static void main(String[] args) {
        try (Drawer c = new Drawer("A"); Drawer d = new Drawer("B")) {
            System.out.println("using " + c + " and " + d);
            throw new IllegalStateException("error during use");
        } catch (IllegalStateException e) {
            System.out.println("Caught: " + e.getMessage());
            for (Throwable s : e.getSuppressed()) System.out.println("  suppressed: " + s.getMessage());
        } finally {
            System.out.println("finally");
        }
    }
}
class Drawer implements AutoCloseable {
    private final String name;
    Drawer(String name) { this.name = name; System.out.println("open " + name); }
    @Override public void close() {
        System.out.println("close " + name);
        throw new IllegalStateException("error closing " + name);
    }
    @Override public String toString() { return name; }
}
