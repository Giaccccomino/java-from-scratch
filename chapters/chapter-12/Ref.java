public class Ref {
    public static void main(String[] args) {
        Room a = new Room("Screen 1", 120);
        Room b = a;
        b.book(10);
        System.out.println(a.sold);
        Room c = new Room("Screen 1", 120);
        c.book(10);
        System.out.println((a == c) + " " + a.equals(c) + " " + (a == b));
        Room d = null;
        System.out.println(d);
        System.out.println(d.getFree());
    }
}
class Room {
    String name; int capacity; int sold;
    Room(String name, int capacity) { this.name = name; this.capacity = capacity; }
    boolean book(int s) { sold += s; return true; }
    int getFree() { return capacity - sold; }
}
