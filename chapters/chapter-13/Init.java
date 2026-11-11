public class Init {
    static final String[] DAYS;
    static {
        DAYS = new String[7];
        String[] p = "mon tue wed thu fri sat sun".split(" ");
        for (int i = 0; i < 7; i++) DAYS[i] = p[i].toUpperCase();
        System.out.println("static block executed");
    }

    int x;
    { x = 42; System.out.println("instance block"); }

    Init() { System.out.println("constructor, x = " + x); }

    public static void main(String[] a) {
        System.out.println("main starts");
        System.out.println(String.join(",", DAYS));
        new Init();
        new Init();
    }
}
