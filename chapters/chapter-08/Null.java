public class Null {
    public static void main(String[] args) {
        String code = null;
        switch (code) {
            case "FULL" -> System.out.println("Full price");
            default -> System.out.println("Other");
        }
    }
}
