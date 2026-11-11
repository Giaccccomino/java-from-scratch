public class U {
    public static void main(String[] args) {
        String a = "Aurora";
        String b = "Aurora";
        String c = new String("Aurora");
        String d = "Aur" + "ora";
        String part = "Aur";
        String e = part + "ora";
        System.out.println((a == b) + " " + (a == c) + " " + (a == d) + " " + (a == e) + " " + a.equals(e) + " " + (a == e.intern()));
        String title = "a night in the city";
        title.toUpperCase();
        System.out.println(title);
        title = title.toUpperCase();
        System.out.println(title);
        String nothing = null;
        System.out.println("Aurora".equals(nothing));
        System.out.println(java.util.Objects.equals(nothing, "x"));
        char ch = '7';
        System.out.println(Character.isDigit(ch) + " " + Character.isLetter('F') + " " + Character.isUpperCase('F') + " " + Character.toLowerCase('F') + " " + Character.getNumericValue('7') + " " + (ch - '0') + " " + Character.isWhitespace(' '));
        String emoji = "Cinema 🍿";
        System.out.println(emoji + " " + emoji.length() + " " + emoji.codePointCount(0, emoji.length()));
        System.out.println(nothing.length());
    }
}
