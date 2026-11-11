public class Rules { public static void main(String[] a) {} }
class Base {
    public String name() { return "base"; }
    final void fixed() {}
}
class Derived extends Base {
    String name() { return "derived"; }
    void fixed() {}
}
final class Sealed {}
class Child extends Sealed {}
