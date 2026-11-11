public class Shadow {
    public static void main(String[] args) { new Cinema("Aurora").new Screen("Screen 1").print(); }
}
class Cinema {
    private final String name;
    Cinema(String name) { this.name = name; }
    class Screen {
        private final String name;
        Screen(String name) { this.name = name; }
        void print() {
            System.out.println(name + " of the " + Cinema.this.name + " cinema");
        }
    }
}
