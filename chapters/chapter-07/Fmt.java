import java.util.Locale;
public class Fmt {
    public static void main(String[] args) {
        System.out.printf("[%d] [%5d] [%-5d] [%05d] [%,d]%n", 42, 42, 42, 42, 1250000);
        System.out.printf("[%f] [%.2f] [%8.2f] [%-8.2f] [%,.2f]%n", 9.5, 9.5, 9.5, 9.5, 12345.678);
        System.out.printf("[%s] [%10s] [%-10s] [%.3s]%n", "Aurora", "Aurora", "Aurora", "Aurora");
        System.out.printf("[%c] [%b] [%e] [%x] [%%]%n", 'F', true, 1500000.0, 255);
        System.out.printf(Locale.GERMANY, "%.2f %,d%n", 9.5, 1250000);
        System.out.printf("%-22s %6s %8s%n", "Movie", "Screen", "Price");
        System.out.printf("%-22s %6d %8.2f%n", "A Night in the City", 2, 9.5);
        System.out.printf("%-22s %6d %8.2f%n", "The Secret Library", 3, 7.0);
        String s = String.format("%03d", 7); System.out.println(s);
        System.out.printf("%2$s %1$s%n", "world", "hello");
        System.out.println(java.text.NumberFormat.getCurrencyInstance(Locale.US).format(1234.5));
        System.out.printf("%d%n", 9.5);
    }
}
