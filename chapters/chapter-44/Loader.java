public class Loader {
    public static void main(String[] a) {
        System.out.println("Loader:      " + Loader.class.getClassLoader());
        System.out.println("parent:      " + Loader.class.getClassLoader().getParent());
        System.out.println("java.sql:    " + java.sql.Connection.class.getClassLoader());
        System.out.println("String:      " + String.class.getClassLoader());
        Runtime r = Runtime.getRuntime();
        System.out.printf("max heap %d MB, total %d MB, free %d MB, processors %d%n", r.maxMemory() >> 20, r.totalMemory() >> 20, r.freeMemory() >> 20, r.availableProcessors());
    }
}
