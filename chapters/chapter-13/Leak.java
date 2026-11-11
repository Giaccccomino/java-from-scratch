import java.util.Arrays;
public class Leak {
    public static void main(String[] args) {
        int[] times = {1530, 1800, 2030};
        Program p = new Program(times);
        times[0] = 999;
        p.getTimes()[1] = 0;
        System.out.println(Arrays.toString(p.getTimes()));
        int[] others = {1530, 1800, 2030};
        SafeProgram q = new SafeProgram(others);
        others[0] = 999;
        q.getTimes()[1] = 0;
        System.out.println(Arrays.toString(q.getTimes()));
    }
}
class Program {
    private final int[] times;
    Program(int[] times) { this.times = times; }
    int[] getTimes() { return times; }
}
class SafeProgram {
    private final int[] times;
    SafeProgram(int[] times) { this.times = times.clone(); }
    int[] getTimes() { return times.clone(); }
}
