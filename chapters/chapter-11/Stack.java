public class Stack {
    static int average(int[] v) { return sum(v) / v.length; }
    static int sum(int[] v) { int s = 0; for (int x : v) s += x; return s; }
    public static void main(String[] args) {
        System.out.println(average(new int[]{}));
    }
}
