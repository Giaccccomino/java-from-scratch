public class A {
    public static void main(String[] args) {
        int free = -2;
        assert free >= 0 : "negative free seats: " + free;
        System.out.println("end");
    }
}
