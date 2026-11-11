public class Over {
    static double total(int tickets, double price) { return tickets * price; }
    static double total(int tickets) { return total(tickets, 9.50); }
    static double total(double... prices) { double s = 0; for (double p : prices) s += p; return s; }
    static void f(long x) { System.out.println("long"); }
    static void f(Integer x) { System.out.println("Integer"); }
    static void f(int... x) { System.out.println("varargs"); }
    static void g(double x) { System.out.println("g double"); }
    public static void main(String[] args) {
        System.out.println(total(3, 7.0));
        System.out.println(total(3));
        System.out.println(total(9.5, 7.0, 6.0));
        f(5);
        g(5);
    }
}
