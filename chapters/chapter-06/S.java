public class S {
    public static void main(String[] args) {
        String title = "A Night in the City";
        System.out.println(title.length());
        System.out.println(title.charAt(0) + " " + title.charAt(title.length() - 1));
        System.out.println(title.substring(15) + "|" + title.substring(0, 7) + "|" + title.substring(2, 3));
        System.out.println(title.indexOf("City") + " " + title.indexOf('i') + " " + title.lastIndexOf('i') + " " + title.indexOf("Rome"));
        System.out.println(title.contains("City") + " " + title.startsWith("A Night") + " " + title.endsWith("ity"));
        System.out.println(title.toUpperCase() + " " + title.toLowerCase());
        String messy = "   Anna Smith  ";
        System.out.println("[" + messy.strip() + "] [" + messy.trim() + "] " + messy.isBlank() + " " + "".isEmpty() + " " + "  ".isBlank() + " " + "  ".isEmpty());
        System.out.println(title.replace('i', '1') + " " + title.replace("City", "Valley"));
        String line = "Screen 2,8:45 PM,A Night in the City,9.50";
        String[] parts = line.split(",");
        System.out.println(parts.length + " " + parts[2] + " " + parts[3]);
        System.out.println(String.join(" | ", "Screen 1", "Screen 2", "Screen 3"));
        System.out.println("=".repeat(20));
        System.out.println(String.valueOf(120) + 1);
        int seats = Integer.parseInt("120"); double price = Double.parseDouble("9.50");
        System.out.println(seats + 1 + " " + price * 2);
        System.out.println("Row\tSeat\nF\t12");
        System.out.println("The movie \"A Night in the City\" in folder C:\\cinema");
        String t = """
            AURORA CINEMA
              Screen 1  6:30 PM
              Screen 2  8:45 PM
            """;
        System.out.print(t);
        System.out.println("%s ticket: $%.2f".formatted("Full-price", 9.5));
        System.out.println(title.chars().filter(c -> c == 'i').count());
        System.out.println("abc".compareTo("abd") + " " + "b".compareTo("a") + " " + "Zeta".compareTo("alpha") + " " + "zeta".compareToIgnoreCase("Alpha"));
        System.out.println("hello".equalsIgnoreCase("HELLO"));
        System.out.println(title.indexOf("i", 5));
        System.out.println(title.toUpperCase().charAt(2));
    }
}
