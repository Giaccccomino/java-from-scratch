public class NoDefault {
    public static void main(String[] args) {
        Screen s = new Screen();
    }
}
class Screen {
    String name; int capacity;
    Screen(String name, int capacity) { this.name = name; this.capacity = capacity; }
}
