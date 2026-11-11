public class Trap {
    public static void main(String[] args) { new Vip("F12"); }
}
class Base {
    Base() { System.out.println("Base: " + describe()); }
    String describe() { return "ticket"; }
}
class Vip extends Base {
    private final String seat;
    Vip(String seat) { super(); this.seat = seat; System.out.println("Vip: " + describe()); }
    @Override String describe() { return "VIP seat " + seat.toUpperCase(); }
}
