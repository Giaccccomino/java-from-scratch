public class Bin {
    static int search(int[] a, int x, int lo, int hi) {
        if (lo > hi) return -1;
        int m = (lo + hi) >>> 1;
        if (a[m] == x) return m;
        return a[m] < x ? search(a, x, m + 1, hi) : search(a, x, lo, m - 1);
    }
    public static void main(String[] args) {
        int[] v = {280, 301, 312, 455, 520, 760, 890};
        System.out.println(search(v, 520, 0, v.length - 1) + " " + search(v, 400, 0, v.length - 1));
    }
}
