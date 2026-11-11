public class Err { public static void main(String[] a) { Bookable b = new Bookable(); } }
interface Bookable { boolean book(int seats); }
class Box implements Bookable { boolean book(int seats) { return true; } }
class Empty implements Bookable { }
