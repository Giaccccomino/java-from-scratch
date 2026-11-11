public class Shadow {
    public static void main(String[] args) {
        Hall s = new Hall("Screen 1");
        System.out.println(s.name);          // null: the constructor assigned the parameter to itself
    }
}
class Hall {
    String name;
    Hall(String name) { name = name; }
}
