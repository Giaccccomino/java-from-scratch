public class Str {
    public static void main(String[] args) {
        String a = "wed";
        String b = new String("wed");
        System.out.println(a == b);
        System.out.println(a.equals(b));
        System.out.println("WED".equalsIgnoreCase(b));
        String c = null;
        System.out.println("wed".equals(c));
    }
}
