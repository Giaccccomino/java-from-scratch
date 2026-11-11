import java.nio.file.*;
public class Checked {
    public static void main(String[] a) {
        String s = Files.readString(Path.of("prices.txt"));
    }
}
