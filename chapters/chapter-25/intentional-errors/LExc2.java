import java.nio.file.*;
import java.util.*;
public class LExc2 { public static void main(String[] a) { List.of("a").forEach(p -> Files.readString(Path.of(p))); } }
