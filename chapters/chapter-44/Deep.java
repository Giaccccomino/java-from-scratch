public class Deep {
    static int depth = 0;
    static void down() { depth++; down(); }
    public static void main(String[] a) {
        try { down(); } catch (StackOverflowError e) { System.out.println("StackOverflowError at depth " + depth); }
    }
}
