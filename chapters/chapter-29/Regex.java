import java.util.*;
import java.util.regex.*;

public class Regex {
    public static void main(String[] args) {
        for (String code : List.of("C5", "C12", "c5", "Z99x", "AA1", "H0"))
            System.out.print(code + "=" + code.matches("[A-H](?:[1-9]|1[0-9]|20)") + " ");
        System.out.println();
        Pattern email = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
        for (String e : List.of("john@example.com", "anna.j+cinema@mail.co.uk", "no-at-sign", "a@b"))
            System.out.print(e + "=" + email.matcher(e).matches() + " ");
        System.out.println();

        String log = "2026-11-14 20:31 SALE Skyline x3 28.50; 2026-11-14 20:45 SALE River x2 19.00; 2026-11-14 21:02 REFUND Skyline x1";
        Pattern p = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}) SALE (\\w+) x(\\d+) (\\d+\\.\\d{2})");
        Matcher m = p.matcher(log);
        while (m.find()) {
            System.out.println("time " + m.group(2) + " movie " + m.group(3) + " tickets " + m.group(4) + " amount " + m.group(5) + " [" + m.start() + "-" + m.end() + "]");
        }
        Pattern named = Pattern.compile("(?<movie>\\w+) x(?<n>\\d+)");
        Matcher mn = named.matcher("Skyline x3");
        if (mn.find()) System.out.println(mn.group("movie") + " " + Integer.parseInt(mn.group("n")));
        System.out.println(Pattern.compile("\\d+").matcher("screen 1, screen 12, screen 3").results().map(MatchResult::group).toList());

        System.out.println("A   Night  in the City".replaceAll("\\s+", " ") + " | " + "2026-11-14".replaceAll("(\\d+)-(\\d+)-(\\d+)", "$2/$3/$1") + " | " + "Skyline x3".replaceFirst("x(\\d)", "times $1"));
        System.out.println(Arrays.toString("a, b,,c ;d".split("[,;]\\s*")) + " " + Arrays.toString("a,b,,".split(",")) + " " + Arrays.toString("a,b,,".split(",", -1)) + " " + Arrays.toString("1.2.3".split(".")) + " " + Arrays.toString("1.2.3".split("\\.")));
        System.out.println("price: 9.50 dollars".replaceAll(".", "*").length() + " " + "9.50".replace(".", ",") + " " + Pattern.quote("9.50"));
        System.out.println(Pattern.compile("skyline", Pattern.CASE_INSENSITIVE).matcher("The SKYLINE").find() + " " + "The SKYLINE".matches("(?i).*skyline.*"));
        System.out.println("aaa".matches("a*") + " " + "<b>one</b><b>two</b>".replaceAll("<b>.*</b>", "X") + " " + "<b>one</b><b>two</b>".replaceAll("<b>.*?</b>", "X"));
        try { Pattern.compile("(\\d+"); } catch (PatternSyntaxException e) { System.out.println(e.getDescription() + " @" + e.getIndex()); }
        Matcher ms = Pattern.compile("x(\\d)").matcher("Skyline x3");
        try { ms.group(1); } catch (IllegalStateException e) { System.out.println("group before find: " + e.getMessage()); }
        System.out.println(Pattern.compile("\\d+").matcher("ab123cd").replaceAll(r -> String.valueOf(Integer.parseInt(r.group()) * 2)));
        long t0 = System.nanoTime();
        try {
            String bad = "a".repeat(28) + "!";
            boolean ok = bad.matches("(a+)+$");
            System.out.println("backtracking: " + ok + " in " + (System.nanoTime() - t0) / 1_000_000 + " ms");
        } catch (StackOverflowError e) { System.out.println("SO"); }
    }
}
