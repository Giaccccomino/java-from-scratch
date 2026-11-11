import java.io.IOException;
import java.nio.file.*;
public class Checked2 {
    static String readPriceList(String file) throws IOException {
        return Files.readString(Path.of(file));
    }
    public static void main(String[] a) {
        try {
            System.out.println(readPriceList("prices.txt"));
        } catch (NoSuchFileException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Read error: " + e);
        }
    }
}
