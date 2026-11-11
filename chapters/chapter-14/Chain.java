public class Chain {
    public static void main(String[] args) { new C(); }
}
class A { A() { System.out.println("A"); } }
class B extends A { B() { System.out.println("B"); } }
class C extends B { int x = print(); C() { super(); System.out.println("C"); } int print() { System.out.println("field of C"); return 1; } }
