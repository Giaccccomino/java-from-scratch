public class Hanoi {
    static int moves = 0;
    static void hanoi(int n, char from, char to, char via) {
        if (n == 0) return;
        hanoi(n - 1, from, via, to);
        moves++;
        if (n == 3 || moves <= 3) System.out.println("Move " + moves + ": disk " + n + " from " + from + " to " + to);
        hanoi(n - 1, via, to, from);
    }
    public static void main(String[] args) { hanoi(3, 'A', 'C', 'B'); System.out.println("Total: " + moves); }
}
