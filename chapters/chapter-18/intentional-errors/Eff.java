public class Eff {
    public static void main(String[] args) {
        int counter = 0;
        Runnable r = new Runnable() {
            @Override public void run() { counter++; }
        };
        String msg = "hello";
        msg = "other";
        Runnable q = () -> System.out.println(msg);
    }
}
