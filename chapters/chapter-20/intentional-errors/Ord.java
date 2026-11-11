public class Ord {
    public static void main(String[] a) {
        try { Integer.parseInt("x"); }
        catch (Exception e) { }
        catch (NumberFormatException e) { }
    }
}
