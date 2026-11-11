public class Shallow {
    public static void main(String[] a) throws Exception {
        P p = new P(); p.v[0] = true;
        P c = (P) p.clone();
        c.v[1] = true;
        System.out.println(p.v[1]);
        try { new Q().clone2(); } catch (CloneNotSupportedException e) { System.out.println("Exception: " + e); }
    }
}
class P implements Cloneable { boolean[] v = new boolean[2]; public Object clone() throws CloneNotSupportedException { return super.clone(); } }
class Q { Object clone2() throws CloneNotSupportedException { return super.clone(); } }
