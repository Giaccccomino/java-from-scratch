import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class TextFiles {
    record Sale(String movie, String day, int tickets, long takings) {
        static Sale fromCsv(String line) {
            String[] c = line.split(",", -1);
            return new Sale(c[0], c[1], Integer.parseInt(c[2]), Long.parseLong(c[3]));
        }
        String toCsv() { return String.join(",", movie, day, String.valueOf(tickets), String.valueOf(takings)); }
    }

    public static void main(String[] args) throws IOException {
        Path folder = Path.of("data");
        Path file = folder.resolve("sales.csv");
        System.out.println(file + " | " + file.getFileName() + " | " + file.getParent() + " | " + file.toAbsolutePath().isAbsolute() + " | " + Files.exists(file));
        Files.createDirectories(folder);
        List<Sale> sales = List.of(new Sale("Skyline", "sat", 120, 114000), new Sale("River", "sat", 80, 76000), new Sale("Hilltop Café", "sun", 40, 38000));
        List<String> lines = new ArrayList<>();
        lines.add("movie,day,tickets,takings");
        for (Sale s : sales) lines.add(s.toCsv());
        Files.write(file, lines);
        System.out.println(Files.exists(file) + " " + Files.size(file) + " bytes");
        System.out.print(Files.readString(file));
        List<String> read = Files.readAllLines(file);
        System.out.println(read.size() + " lines");
        List<Sale> s2 = read.stream().skip(1).map(Sale::fromCsv).toList();
        System.out.println(s2.get(2) + " total " + s2.stream().mapToLong(Sale::takings).sum());
        try (Stream<String> s = Files.lines(file)) {
            System.out.println(s.skip(1).filter(r -> r.contains(",sat,")).count() + " Saturday sales");
        }
        Files.writeString(file, "Lighthouse,mon,30,18000\n", StandardOpenOption.APPEND);
        System.out.println(Files.readAllLines(file).getLast());
        Path copy = folder.resolve("sales-backup.csv");
        Files.copy(file, copy, StandardCopyOption.REPLACE_EXISTING);
        try { Files.copy(file, copy); } catch (FileAlreadyExistsException e) { System.out.println("FileAlreadyExistsException: " + e.getMessage()); }
        Files.move(copy, folder.resolve("archive.csv"), StandardCopyOption.REPLACE_EXISTING);
        try { Files.readString(Path.of("data/missing.txt")); } catch (NoSuchFileException e) { System.out.println("NoSuchFileException: " + e.getMessage()); }
        Files.writeString(folder.resolve("latin1.txt"), "Café", StandardCharsets.ISO_8859_1);
        try { Files.readString(folder.resolve("latin1.txt")); } catch (java.nio.charset.MalformedInputException e) { System.out.println("MalformedInputException: " + e.getMessage()); }
        System.out.println(Files.readString(folder.resolve("latin1.txt"), StandardCharsets.ISO_8859_1) + " " + Files.size(folder.resolve("latin1.txt")) + " vs " + "Café".getBytes(StandardCharsets.UTF_8).length);
        System.out.println(new String(Files.readAllBytes(folder.resolve("latin1.txt")), StandardCharsets.UTF_8));

        try (BufferedWriter w = Files.newBufferedWriter(folder.resolve("report.txt"))) {
            w.write("REPORT"); w.newLine();
            for (Sale s : s2) { w.write("%-18s %4d".formatted(s.movie(), s.tickets())); w.newLine(); }
        }
        try (BufferedReader r = Files.newBufferedReader(folder.resolve("report.txt"))) {
            String line; int n = 0;
            while ((line = r.readLine()) != null) { n++; System.out.println(n + ": " + line); }
        }
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(folder.resolve("pw.txt")))) {
            pw.printf("Total: %.2f%n", 2280.0);
        }
        System.out.print(Files.readString(folder.resolve("pw.txt")));
        System.out.println(Path.of("data/../data/./sales.csv").normalize() + " " + folder.relativize(file) + " " + Path.of("/home/claude").resolve("x").getNameCount());
    }
}
