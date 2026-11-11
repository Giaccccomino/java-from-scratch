import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;
import java.util.stream.*;

public class Folders {
    record Booking(String code, String customer, int seats) implements Serializable {}

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Path base = Path.of("archive");
        for (String m : List.of("2026-09", "2026-10", "2026-11")) {
            Path d = Files.createDirectories(base.resolve(m));
            for (int g = 1; g <= 3; g++) Files.writeString(d.resolve("takings-" + g + ".csv"), "x".repeat(g * 100));
        }
        Files.writeString(base.resolve("readme.txt"), "takings archive");
        try (Stream<Path> s = Files.list(base)) { System.out.println(s.map(Path::getFileName).map(Path::toString).sorted().toList()); }
        try (Stream<Path> s = Files.walk(base)) {
            List<Path> csv = s.filter(Files::isRegularFile).filter(p -> p.toString().endsWith(".csv")).sorted().toList();
            System.out.println(csv.size() + " csv files, the first " + csv.get(0));
        }
        try (Stream<Path> s = Files.walk(base)) {
            long total = s.filter(Files::isRegularFile).mapToLong(p -> { try { return Files.size(p); } catch (IOException e) { throw new UncheckedIOException(e); } }).sum();
            System.out.println("total space: " + total + " bytes");
        }
        try (Stream<Path> s = Files.find(base, 5, (p, a) -> a.size() > 250 && p.toString().endsWith(".csv"))) { System.out.println(s.count() + " files over 250 bytes"); }
        try { Files.delete(base); } catch (DirectoryNotEmptyException e) { System.out.println("DirectoryNotEmptyException: " + e.getMessage()); }
        BasicFileAttributes at = Files.readAttributes(base.resolve("readme.txt"), BasicFileAttributes.class);
        System.out.println(at.size() + " " + at.isDirectory() + " " + (at.lastModifiedTime() != null));

        Path img = base.resolve("poster.bin");
        byte[] data = new byte[5_000_000];
        new Random(1).nextBytes(data);
        Files.write(img, data);
        long t0 = System.nanoTime();
        try (InputStream in = Files.newInputStream(img); OutputStream out = Files.newOutputStream(base.resolve("copy1.bin"))) {
            int b; while ((b = in.read()) != -1) out.write(b);
        }
        long t1 = System.nanoTime();
        try (InputStream in = new BufferedInputStream(Files.newInputStream(img)); OutputStream out = new BufferedOutputStream(Files.newOutputStream(base.resolve("copy2.bin")))) {
            int b; while ((b = in.read()) != -1) out.write(b);
        }
        long t2 = System.nanoTime();
        try (InputStream in = Files.newInputStream(img); OutputStream out = Files.newOutputStream(base.resolve("copy3.bin"))) { in.transferTo(out); }
        long t3 = System.nanoTime();
        System.out.printf("byte by byte %d ms, buffered %d ms, transferTo %d ms, equal %b%n", (t1 - t0) / 1_000_000, (t2 - t1) / 1_000_000, (t3 - t2) / 1_000_000,
            Arrays.equals(Files.readAllBytes(img), Files.readAllBytes(base.resolve("copy1.bin"))) && Files.mismatch(img, base.resolve("copy3.bin")) == -1);

        Path ser = base.resolve("bookings.ser");
        List<Booking> list = new ArrayList<>(List.of(new Booking("AUR-1", "Smith", 2), new Booking("AUR-2", "Johnson", 4)));
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(ser))) { oos.writeObject(list); }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(ser))) {
            @SuppressWarnings("unchecked") List<Booking> read = (List<Booking>) ois.readObject();
            System.out.println(read + " " + Files.size(ser) + " bytes");
        }
        Properties conf = new Properties();
        conf.setProperty("price.full", "950");
        conf.setProperty("cinema.name", "Aurora");
        try (Writer w = Files.newBufferedWriter(base.resolve("config.properties"))) { conf.store(w, "Configuration"); }
        Properties loaded = new Properties();
        try (Reader r = Files.newBufferedReader(base.resolve("config.properties"))) { loaded.load(r); }
        System.out.println(loaded.getProperty("cinema.name") + " " + loaded.getProperty("max.seats", "10"));
        Path tmp = Files.createTempFile("aurora-", ".tmp");
        System.out.println(tmp.getFileName().toString().startsWith("aurora-") + " " + Files.deleteIfExists(tmp));
        try (Stream<Path> s = Files.walk(base)) {
            s.sorted(Comparator.reverseOrder()).forEach(p -> { try { Files.delete(p); } catch (IOException e) { throw new UncheckedIOException(e); } });
        }
        System.out.println(Files.exists(base));
        File old = new File("data", "sales.csv");
        System.out.println(old.exists() + " " + old.toPath());
    }
}
