import java.util.*;

public class Text {
    public static void main(String[] args) {
        long t0 = System.nanoTime();
        String s = "";
        for (int i = 0; i < 50_000; i++) s += "x";
        long t1 = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50_000; i++) sb.append("x");
        String r = sb.toString();
        long t2 = System.nanoTime();
        System.out.println(s.length() + " " + r.length() + " concat " + (t1 - t0) / 1_000_000 + " ms, sb " + (t2 - t1) / 1_000_000 + " ms");

        StringBuilder b = new StringBuilder("Skyline");
        b.append(' ').append(128).append(" min").insert(0, "[").append(']');
        System.out.println(b + " " + b.length());
        b.reverse(); System.out.println(b);
        b.reverse().setCharAt(1, 's'); System.out.println(b);
        b.delete(0, 1).deleteCharAt(b.length() - 1).replace(0, 7, "SKYLINE"); System.out.println(b + " " + b.indexOf("128"));
        StringBuilder x = new StringBuilder("a"), y = new StringBuilder("a");
        System.out.println(x.equals(y) + " " + x.toString().equals(y.toString()) + " " + (x.compareTo(y) == 0));

        String receipt = """
            AURORA CINEMA
              Movie: %s
              Total: $%.2f
            """.formatted("A Night in the City", 19.0);
        System.out.print(receipt);
        String json = """
            {"movie": "Skyline", \
            "duration": 128}""";
        System.out.println(json);
        String sql = """
            SELECT title
            FROM movie
            WHERE duration > 100\
            """;
        System.out.println("[" + sql + "]");

        String u = "  hello" + (char) 8195;
        System.out.println("|" + u.trim() + "|" + u.strip() + "|" + "   ".isBlank() + " " + "".isEmpty());
        System.out.println("a\nb\nc".lines().count() + " " + "line".indent(2).replace("\n", "$") + " " + "ab".repeat(3));
        System.out.println(String.join("-", List.of("mon", "tue")) + " " + "Skyline".chars().count() + " " + "Skyline".contains("lin") + " " + "skyline".compareToIgnoreCase("SKYLINE"));
        StringJoiner sj = new StringJoiner(", ", "{", "}");
        sj.add("a").add("b"); sj.setEmptyValue("empty");
        System.out.println(sj + " " + new StringJoiner(",").setEmptyValue("empty"));
        System.out.println("%-10s|%5d|%08.3f|%x|%e|%b".formatted("River", 42, 3.14159, 255, 12345.678, true));
        System.out.println(String.valueOf(3.5) + String.valueOf(true) + String.valueOf(new char[]{'o', 'k'}));
        char[] c = "skyline".toCharArray(); Arrays.sort(c); System.out.println(new String(c));
        System.out.println("café".length() + " " + "😀".length() + " " + "😀".codePointCount(0, 2));
    }
}
