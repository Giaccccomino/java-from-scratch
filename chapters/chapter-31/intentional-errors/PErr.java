public class PErr {
    static String f(Object o) {
        return switch (o) {
            case CharSequence cs -> "cs";
            case String s -> "s";
            default -> "x";
        };
    }
    static String g(Object o) {
        return switch (o) {
            case String s -> "s";
        };
    }
    public static void main(String[] a) {}
}
